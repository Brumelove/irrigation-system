package com.andela.irrigationsystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * This class helps manage configuration for rabbitmq setup
 *
 * @author Brume
 */
@Configuration
@RequiredArgsConstructor
public class MQConfig {

    private final RabbitMQPropConfig config;

    @Bean
    public Queue emailQueue() {
        return new Queue(config.getEmailQueue());
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(config.getExchange());
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(exchange())
                .with(config.getEmailKey());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
