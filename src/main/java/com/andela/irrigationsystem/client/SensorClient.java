package com.andela.irrigationsystem.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * This class uses feign client to the sensor service
 *
 * @author Brume
 */
@FeignClient(value = "SENSOR-SERVICE")
public interface SensorClient {

    @PostMapping("/api/v1/pong/check")
    String pong(String ping);
}
