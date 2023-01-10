package com.andela.irrigationsystem.dto;

import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.enumerations.StatusType;
import com.andela.irrigationsystem.model.Plot;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Brume
 **/

@Getter
@Setter
public class TimeSlotsDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotNull(message = "dayValue cannot be empty")
    private int dayValue;
    @NotNull(message = "timeValue cannot be empty")
    private int timeValue;
    @NotBlank(message = "sensorNumber cannot be empty")
    private String sensorNumber;
    @JsonIgnore
    private String humanReadableTime;

    @NotNull(message = "cubicWaterAmount cannot be empty")
    private Double cubicWaterAmount;
    private StatusType status;
    private FrequencyType frequency;

    @JsonIgnore
    private Plot plot;

}
