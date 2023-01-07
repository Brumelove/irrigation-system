package com.andela.irrigationsystem.listener;

import com.andela.irrigationsystem.event.EmailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * This class provides a method the listens to the sms queue for message
 * and delivers when it finds a request
 *
 * @author Brume
 * @version 1.0
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class EmailEventListener {

    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    public void handleSmsNotification(EmailEvent emailEvent) {
        //At this point a message sending service is called to deliver message to recipient
        log.info("---> Message::[{}] with referenceNumber::{} from source::{} has been sent to destination::{} successfully",
                emailEvent.message(), emailEvent.referenceNumber(), emailEvent.sourceAddress(),
                emailEvent.destinationAddress());
    }
}
