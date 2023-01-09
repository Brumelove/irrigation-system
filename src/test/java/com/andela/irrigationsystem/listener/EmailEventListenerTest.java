package com.andela.irrigationsystem.listener;

import com.andela.irrigationsystem.event.EmailEvent;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Brume
 **/
@ExtendWith(MockitoExtension.class)
class EmailEventListenerTest {

    @Test
    void handleEmailNotification() {
        EmailEvent emailEvent = new EmailEvent("testId", RandomStringUtils.randomAlphanumeric(4), RandomStringUtils.randomAlphanumeric(3), RandomStringUtils.randomAlphanumeric(5));

        EmailEventListener.handleEmailNotification(emailEvent);

        assertEquals("testId", emailEvent.referenceNumber());
    }
}