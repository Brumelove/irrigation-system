package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.config.RabbitMQPropConfig;
import com.andela.irrigationsystem.dto.EmailDto;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Brume
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class SensorService {
    private final RabbitTemplate template;
    private final RabbitMQPropConfig config;

    @Retry(name = "triggerSensor", fallbackMethod = "sendMessage")
    public void triggerSensor() {
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
    public String sendMessage(EmailDto emailDto) {
        String messageId = UUID.randomUUID().toString() +
                LocalDateTime.now().getNano();
        emailDto.setMessageId(messageId);

        log.info("Notification message sent -> {}", emailDto.getRecipientsAddress());
        template.convertAndSend(config.getExchange(), config.getNotificationJsonKey(), emailDto);

        return messageId;
    }


}

