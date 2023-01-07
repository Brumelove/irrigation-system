package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.dto.TimeSlotsDto;
import com.andela.irrigationsystem.mapper.IrrigationMapper;
import com.andela.irrigationsystem.model.TimeSlots;
import com.andela.irrigationsystem.repositories.TimeSlotsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author Brume
 **/
@Service
@RequiredArgsConstructor
public class TimeSlotsService {
    public final IrrigationMapper mapper;
    private final TimeSlotsRepository repository;
    private final PlotService plotService;

    public TimeSlots save(TimeSlots timeSlots) {

        return repository.save(timeSlots);
    }


    public TimeSlots getById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, id + " sensor id does not exist"));
    }

    public List<TimeSlotsDto> getAllTimeSlotsDto() {
        return mapper.mapTimeSlotsEntityListToDtoList(repository.findAll());
    }

    public List<TimeSlotsDto> getTimeSlotsDtoByPlotId(Long plotId) {
        return mapper.mapTimeSlotsEntityListToDtoList(repository.findByPlot_Id(plotId));
    }
}
