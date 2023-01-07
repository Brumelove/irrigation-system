package com.andela.irrigationsystem.dto;

import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.enumerations.StatusType;
import com.andela.irrigationsystem.model.Plot;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Brume
 **/

@Getter
@Setter
public class TimeSlotsDto {
    @ApiModelProperty(hidden = true)
    private Long id;
    private int weekDay;
    private int timeValue;
    private Double cubicWaterAmount;
    private StatusType status = StatusType.PENDING;
    private FrequencyType frequency;
    private int numberOfRetries;

    @ApiModelProperty(hidden = true)
    private Plot plot;

}
