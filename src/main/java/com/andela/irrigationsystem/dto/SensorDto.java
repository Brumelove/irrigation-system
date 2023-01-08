package com.andela.irrigationsystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Brume
 **/

@Getter
@Setter
public class SensorDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String sensorNumber;
    private Long plotId;
    private String sensorsEndpoint;
}
