package com.andela.irrigationsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


/**
 * @author Brume
 **/

@Getter
@Setter
public class PlotDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String ownerName;
    private String location;
    private String cropType;
    private String cropName;
    private Long sizeInMeters;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<TimeSlotsDto> timeSlots;

}
