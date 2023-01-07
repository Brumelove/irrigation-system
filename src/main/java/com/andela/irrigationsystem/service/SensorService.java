package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.client.SensorClient;
import com.andela.irrigationsystem.config.RabbitMQPropConfig;
import com.andela.irrigationsystem.dto.EmailDto;
import com.andela.irrigationsystem.dto.TimeSlotsDto;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
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
    private final SensorClient sensorClient;
    private final RabbitMQPropConfig config;

    private final TaskScheduler taskScheduler;

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    @Retry(name = "triggerSensor", fallbackMethod = "sendMessage")
    public boolean triggerSensor(Long plotId, TimeSlotsDto timeSlotsDto) {
        var cronExpression = convertToCronExpression(timeSlotsDto);

        return scheduleATask(timeSlotsDto.getId().toString(), cronExpression, plotId);


    }

    private boolean scheduleATask(String jobId, String cronExpression, Long plotId) {
        //set's task
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(irrigateLand(plotId),
                //trigger the cronjob
                new CronTrigger(cronExpression, TimeZone.getTimeZone(ZoneId.of("UTC"))));
        return scheduledTask.isDone();
    }

    private Runnable irrigateLand(Long plotId) {
        return () -> {
            log.info("LAND IRRIGATION STARTED FOR PLOT " + plotId);

            var pong = sensorClient.pong("Ping");
            if (pong.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Sensor unreachable");
            }
            log.info("LAND IRRIGATION COMPLETED FOR PLOT " + plotId);

        };
    }

    private String convertToCronExpression(TimeSlotsDto timeSlotsDto) {
        int timeValue = timeSlotsDto.getTimeValue();
        int dayOfTheWeek = timeSlotsDto.getWeekDay();

        String cronExpression = switch (timeSlotsDto.getFrequency()) {
            case HOURLY -> "0 0 " + timeValue + "* * *";
            case DAILY -> "* *  * " + timeValue + "* *";
            case BIWEEKLY -> "0 0 " + timeValue + "* * " + dayOfTheWeek;
            case MONTHLY -> "0 0 0 " + timeValue + "* *";
            case YEARLY -> "0 0 0 " + timeValue + " " + dayOfTheWeek + " *";
            default -> "0 0 0 * * *";
        };
        log.info("CRON EXPRESSION ::: {} {}", timeSlotsDto.getFrequency(), cronExpression);
        return cronExpression;
    }

    /**
     * This method processes message delivery by:
     * checking if message is a spam
     * sending message to charging service irrespective of spam status
     * rejecting message when its a spam
     * sending message when to recipient when not a spam
     *
     * @param emailDto contains email information required for delivery
     */
    private String sendMessage(EmailDto emailDto) {
        String messageId = UUID.randomUUID().toString() +
                LocalDateTime.now().getNano();
        emailDto.setMessageId(messageId);

        log.info("Notification message sent -> {}", emailDto.getRecipientsAddress());
        template.convertAndSend(config.getExchange(), config.getNotificationJsonKey(), emailDto);

        return messageId;
    }


}

