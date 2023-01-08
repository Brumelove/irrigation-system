package com.andela.irrigationsystem.controller;

import com.andela.irrigationsystem.dto.PlotDto;
import com.andela.irrigationsystem.dto.TimeSlotsDto;
import com.andela.irrigationsystem.exception.BadRequestException;
import com.andela.irrigationsystem.service.PlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Brume
 **/
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/plots")
public class PlotController {
    private final PlotService service;

    @PostMapping()
    public ResponseEntity<PlotDto> addPlot(@Valid @RequestBody PlotDto request) {
        if (request == null) {
            throw new BadRequestException("request can't be null");
        }
        return ResponseEntity.ok().body(service.addPlot(request));
    }

    @PostMapping("/configure/{plotId}")
    public ResponseEntity<TimeSlotsDto> create(@PathVariable Long plotId, @Valid @RequestBody TimeSlotsDto request) {
        if (request == null) {
            throw new BadRequestException("request can't be null");
        }
        return ResponseEntity.ok().body(service.configurePlotOfLand(plotId, request));
    }

    @PutMapping("/{plotId}")
    public ResponseEntity<PlotDto> editAPlotOfLand(@Valid @RequestBody PlotDto request, @PathVariable Long plotId) {
        return ResponseEntity.ok().body(service.editPlot(plotId, request));
    }

    @GetMapping
    public ResponseEntity<List<PlotDto>> getAllPlotsWithDetails() {
        return ResponseEntity.ok().body(service.getAllPlots());
    }


}
