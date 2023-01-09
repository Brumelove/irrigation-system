package com.andela.irrigationsystem.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Brume
 **/
@Getter
@Builder
public class RetryDto {
    private String sensorNumber;
    private Long plotId;

    private Double cubicWater;
}
