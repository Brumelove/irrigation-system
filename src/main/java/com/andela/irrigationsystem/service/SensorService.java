package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.config.RabbitMQPropConfig;
import com.andela.irrigationsystem.dto.EmailDto;
import com.andela.irrigationsystem.dto.SensorDto;
import com.andela.irrigationsystem.dto.TimeSlotsDto;
import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.enumerations.StatusType;
import com.andela.irrigationsystem.exception.ElementNotFoundException;
import com.andela.irrigationsystem.exception.ElementWithSameIDAlreadyExistsException;
import com.andela.irrigationsystem.mapper.IrrigationMapper;
import com.andela.irrigationsystem.repositories.SensorRepository;
import com.andela.irrigationsystem.service.integration.SensorInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.UUID;

/**
 * @author Brume
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SensorService implements SensorInterface {
    private final RabbitTemplate template;
    private final RetryTemplate retryTemplate;
    private final RabbitMQPropConfig config;
    @Autowired
    @Lazy
    public TimeSlotsService timeSlotsService;
    private final SensorRepository repository;
    private final IrrigationMapper mapper;
    private final TaskScheduler taskScheduler;

    /**
     * Used to schedule the time for a plot of land for irrigation
     *
     * @param plotId
     * @param sensorNumber
     * @param timeSlotsDto
     */
    public void scheduleATask(Long plotId, String sensorNumber, TimeSlotsDto timeSlotsDto) {
        var cronExpression = convertToCronExpression(timeSlotsDto.getFrequency(), timeSlotsDto.getTimeValue(), timeSlotsDto.getDayValue());

        //set's task
        taskScheduler.schedule(irrigateLand(sensorNumber, plotId, timeSlotsDto),
                //trigger the cronjob
                new CronTrigger(cronExpression, TimeZone.getTimeZone(ZoneId.of("UTC"))));
    }

    /**
     * method called on the time duration to irrigate land
     * on failure to pinging the sensor it retries 2 times and on recovery it sends an alert to the neccessary receipient.
     *
     * @param sensorNumber
     * @param plotId
     * @param timeSlots
     * @return Runnable
     */
    private Runnable irrigateLand(String sensorNumber, Long plotId, TimeSlotsDto timeSlots) {
        return () -> {
            log.info("LAND IRRIGATION STARTED FOR PLOT " + plotId);

            try {


                RetryTemplate template = RetryTemplate.builder()
                        .maxAttempts(2)
                        .fixedBackoff(1000)
                        .retryOn(Exception.class)
                        .build();
                template.execute(arg0 -> {
                    var sensorAddress = getSensorAddress(sensorNumber, plotId);
                    return integrateSensor(sensorAddress, timeSlots.getCubicWaterAmount());
                });
                timeSlots.setStatus(StatusType.SUCCESS);
                log.info("LAND IRRIGATION COMPLETED FOR PLOT " + plotId);
            } catch (Exception e) {
                log.info("LAND IRRIGATION FAILED FOR PLOT " + plotId);

                timeSlots.setStatus(StatusType.UNSUCCESSFUL);
                sendMessage();
                log.error(e.getMessage());
            }
            timeSlotsService.update(timeSlots);
        };
    }

    /**
     * converts human readable expression to cron expression for the scheduler
     *
     * @param frequencyType
     * @param timeValue
     * @param day
     * @return String
     */
    public String convertToCronExpression(FrequencyType frequencyType, int timeValue, int day) {
        String cronExpression = switch (frequencyType) {
            case HOURLY -> "0 0 " + timeValue + " * * *";
            case MINUTE -> "0 */" + timeValue + " * ? * *";
            case DAILY -> "0 0 0 " + timeValue + " * *";
            case WEEKLY -> "0 0 " + timeValue + " * * " + day;
            case BIWEEKLY -> "0 0 " + timeValue / 2 + " * * " + day;
            case MONTHLY -> "0 0 " + timeValue + " " + day + " * *";
            case YEARLY -> "0 0 " + timeValue + " " + day + " * *";
            default -> "0 0 0 * * *";
        };
        log.info("CRON EXPRESSION ::: {} {}", frequencyType, cronExpression);
        return cronExpression;
    }

    /**
     * method sends a http post request to the sensor address
     *
     * @param sensorEndpoint
     * @param cubicWater
     * @return ResponseEntity<String>
     */
    @Override
    public ResponseEntity<String> integrateSensor(String sensorEndpoint, Double cubicWater) {
        return WebClient.create().post()
                .uri(sensorEndpoint + "/" + cubicWater)
                .retrieve().toEntity(String.class).block();
    }

    /**
     * This method processes message delivery by:
     * ]     * sending message to a queue if error retrial by the calling method reaches its max attempt
     */
    public void sendMessage() {
        String messageId = UUID.randomUUID().toString() +
                LocalDateTime.now().getNano();

        var emailDto = EmailDto.builder().message("Sensor unResponsive for irrigation")
                .sendersAddress("brumelovee@gmail.com")
                .recipientsAddress("brume.love@andela.com")
                .messageId(messageId)
                .build();

        log.info("Notification message sent to -> {}", emailDto.getRecipientsAddress());
        template.convertAndSend(config.getExchange(), config.getEmailKey(), emailDto);
    }

    /**
     * Gets Sensor address Url
     *
     * @param sensorNumber
     * @param plotId
     * @return String
     */
    public String getSensorAddress(String sensorNumber, Long plotId) {
        return
                repository.getSensorEndpointBySensorNumberAndPlotId(sensorNumber, plotId)
                        .orElseThrow(() -> new ElementNotFoundException("Sensor Address is not be found"));
    }

    /**
     * Method to create a sensor, as part of land configuration
     *
     * @param sensorDto
     * @return SensorDto
     * @throws ElementWithSameIDAlreadyExistsException
     */
    public SensorDto createSensor(SensorDto sensorDto) {
        try {
            var sensor = mapper.mapSensorDtoToEntity(sensorDto);
            return mapper.mapSensorEntityToDto(repository.save(sensor));
        } catch (Exception e) {
            throw new ElementWithSameIDAlreadyExistsException(sensorDto.getSensorNumber() + " is not unique");
        }
    }

}

