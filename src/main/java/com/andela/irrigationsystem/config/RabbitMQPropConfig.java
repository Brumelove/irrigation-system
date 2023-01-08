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

    @Value("${rabbitmq.queue.email.name}")
    private String emailQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.email.key}")
    private String emailKey;

}
