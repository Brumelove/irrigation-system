package com.andela.irrigationsystem.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds retrieved request from sms queue
 *
 * @author Brume
 * @version 1.0
 */
public record EmailEvent(
        @JsonProperty("messageId") String referenceNumber,
        @JsonProperty("sendersAddress") String sourceAddress,
        @JsonProperty("message") String message,
        @JsonProperty("recipientsAddress") String destinationAddress
) {
}
