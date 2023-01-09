package com.andela.irrigationsystem.dto;

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
public class SensorDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "sensorNumber cannot be empty")
    private String sensorNumber;
    @NotNull(message = "plotId cannot be empty")
    private Long plotId;
    @NotBlank(message = "sensorsEndpoint cannot be empty")
    private String sensorsEndpoint;
}
