package com.andela.irrigationsystem.dto;

import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.enumerations.StatusType;
import com.andela.irrigationsystem.model.Plot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Brume
 **/

@Getter
@Setter
public class TimeSlotsDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private int dayValue;
    private int timeValue;
    private String sensorNumber;
    @JsonIgnore
    private String humanReadableTime;

    private Double cubicWaterAmount;
    private StatusType status;
    private FrequencyType frequency;

    @JsonIgnore
    private Plot plot;

}
