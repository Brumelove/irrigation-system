package com.andela.irrigationsystem.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


/**
 * @author Brume
 **/

@Getter
@Setter
public class PlotDto {
    @ApiModelProperty(hidden = true)
    private Long id;
    private String ownerName;
    private String location;
    private String cropType;
    private String cropName;
    private Long size;

    @ApiModelProperty(hidden = true)
    private Set<TimeSlotsDto> timeSlots;

}
