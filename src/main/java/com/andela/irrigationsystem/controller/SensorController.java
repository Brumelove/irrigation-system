package com.andela.irrigationsystem.controller;

import com.andela.irrigationsystem.dto.SensorDto;
import com.andela.irrigationsystem.exception.BadRequestException;
import com.andela.irrigationsystem.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Brume
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/sensor")
public class SensorController {
    private final SensorService service;

    @PostMapping
    public ResponseEntity<SensorDto> addSensor(@Valid @RequestBody SensorDto request) {
        if (request == null) {
            throw new BadRequestException("request can't be null");
        }
        return ResponseEntity.ok().body(service.createSensor(request));
    }

    @PostMapping("/trigger/{cubicAmountOfWater}")
    public ResponseEntity<String> triggerSensor(@PathVariable String cubicAmountOfWater) {

        return ResponseEntity.ok().body("Pong");
    }


}
