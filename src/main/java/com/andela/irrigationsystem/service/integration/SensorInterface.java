package com.andela.irrigationsystem.service.integration;

import org.springframework.http.ResponseEntity;

/**
 * @author Brume
 **/
public interface SensorInterface {
    ResponseEntity<String> integrateSensor(String sensorNumber, Long plotId, Double cubicWater);
}
