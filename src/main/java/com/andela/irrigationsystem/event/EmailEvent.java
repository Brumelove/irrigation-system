package com.andela.irrigationsystem.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds retrieved request from sms queue
 *
 * @author Brume
 * @version 1.0
 */
public record EmailEvent(
        @JsonProperty("referenceNumber") String referenceNumber,
        @JsonProperty("recipientAddress") String sourceAddress,
        @JsonProperty("message") String message,
        @JsonProperty("destinationAddress") String destinationAddress
) {
}
