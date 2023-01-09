package com.andela.irrigationsystem.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Brume
 **/

public record RetryEvent(
        @JsonProperty("sensorNumber") String sensorNumber,
        @JsonProperty("plotId") Long plotId,

        @JsonProperty("cubicWater") Double cubicWater
) {
}
