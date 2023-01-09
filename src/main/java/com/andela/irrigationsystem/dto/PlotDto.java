package com.andela.irrigationsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message = "location cannot be empty")
    private String location;
    private String cropType;
    @NotBlank(message = "cropName cannot be empty")
    private String cropName;
    @NotNull(message = "sizeInMeters cannot be empty")
    private Long sizeInMeters;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Set<TimeSlotsDto> timeSlots;

}
