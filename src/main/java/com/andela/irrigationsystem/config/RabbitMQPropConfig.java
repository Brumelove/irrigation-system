package com.andela.irrigationsystem.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This class helps manage properties for rabbitmq config setup
 *
 * @author Brume
 */
@Component
@Data
public class RabbitMQPropConfig {

    @Value("${rabbitmq.queue.sms.name}")
    private String emailQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.sms.key}")
    private String emailKey;

    @Value("${rabbitmq.routing.charging.key}")
    private String chargingKey;

    @Value("${rabbitmq.routing.notification.key}")
    private String notificationJsonKey;
}
