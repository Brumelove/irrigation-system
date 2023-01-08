package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.config.RabbitMQPropConfig;
import com.andela.irrigationsystem.dto.EmailDto;
import com.andela.irrigationsystem.dto.SensorDto;
import com.andela.irrigationsystem.dto.TimeSlotsDto;
import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.exception.BadRequestException;
import com.andela.irrigationsystem.exception.ElementNotFoundException;
import com.andela.irrigationsystem.mapper.IrrigationMapper;
import com.andela.irrigationsystem.repositories.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Brume
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SensorService {
    private final RabbitTemplate template;
    private final RabbitMQPropConfig config;
    private final SensorRepository repository;
    private final IrrigationMapper mapper;
    private final TaskScheduler taskScheduler;

    public boolean triggerSensor(String sensorNumber, Long plotId, TimeSlotsDto timeSlotsDto) {

        var cronExpression = convertToCronExpression(timeSlotsDto.getFrequency(), timeSlotsDto.getTimeValue(), timeSlotsDto.getDayValue());
        return scheduleATask(cronExpression, plotId, sensorNumber, timeSlotsDto.getCubicWaterAmount()).isDone();
    }

    private ScheduledFuture<?> scheduleATask(String cronExpression, Long plotId, String sensorNumber, Double cubicAmountOfWater) {
        //set's task
        return taskScheduler.schedule(irrigateLand(sensorNumber, plotId, cubicAmountOfWater),
                //trigger the cronjob
                new CronTrigger(cronExpression, TimeZone.getTimeZone(ZoneId.of("UTC"))));
    }

    private Runnable irrigateLand(String sensorNumber, Long plotId, Double cubicAmountOfWater) {
        return () -> {
            log.info("LAND IRRIGATION STARTED FOR PLOT " + plotId);

            ResponseEntity<String> pong = null;
            try {
                pong = pingSensor(sensorNumber, plotId, cubicAmountOfWater);
            } catch (Exception e) {
                sendMessage(e);
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Sensor unreachable");
            }
            log.info("LAND IRRIGATION COMPLETED FOR PLOT " + plotId);

        };
    }

    public String convertToCronExpression(FrequencyType frequencyType, int timeValue, int day) {
        String cronExpression = switch (frequencyType) {
            case HOURLY -> "0 0 " + timeValue + " * * *";
            case MINUTE -> "0 */" + timeValue + " * ? * *";
            case DAILY -> "0 0 0" + timeValue + "* *";
            case WEEKLY -> "0 0 " + timeValue + "* * " + day;
            case BIWEEKLY -> "0 0 " + timeValue + "* * " + day;
            case MONTHLY -> "0 0 0 " + timeValue + "* *";
            case YEARLY -> "0 0 0 " + timeValue + " " + day + " *";
            default -> "0 0 0 * * *";
        };
        log.info("CRON EXPRESSION ::: {} {}", frequencyType, cronExpression);
        return cronExpression;
    }

    /**
     * This method processes message delivery by:
     * checking if message is a spam
     * sending message to charging service irrespective of spam status
     * rejecting message when its a spam
     * sending message when to recipient when not a spam
     */
    @Recover()
    private void sendMessage(Exception e) {
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

    @Retryable(value = Exception.class, maxAttempts = 2, backoff = @Backoff(delay = 10, multiplier = 2))
    private ResponseEntity<String> pingSensor(String sensorNumber, Long plotId, Double cubicWater) {
        var sensor = getSensorAddress(sensorNumber, plotId);

        return WebClient.create().post()
                .uri(sensor + "/" + cubicWater)
                .retrieve().toEntity(String.class).block();
    }

    public String getSensorAddress(String sensorNumber, Long plotId) {
        return
                repository.getSensorEndpointBySensorNumberAndPlotId(sensorNumber, plotId)
                        .orElseThrow(() -> new ElementNotFoundException("Sensor Address is not be found"));
    }

    public SensorDto createSensor(SensorDto sensorDto) {
        try {
            var sensor = mapper.mapSensorDtoToEntity(sensorDto);
            return mapper.mapSensorEntityToDto(repository.save(sensor));
        } catch (Exception e) {
            throw new BadRequestException(sensorDto.getSensorNumber() + " is not unique");
        }
    }

}

