package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.dto.PlotDto;
import com.andela.irrigationsystem.dto.TimeSlotsDto;
import com.andela.irrigationsystem.enumerations.StatusType;
import com.andela.irrigationsystem.exception.ElementNotFoundException;
import com.andela.irrigationsystem.mapper.IrrigationMapper;
import com.andela.irrigationsystem.model.Plot;
import com.andela.irrigationsystem.repositories.PlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Brume
 **/
@Service
@RequiredArgsConstructor
public class PlotService {
    public final IrrigationMapper mapper;
    public final TimeSlotsService timeSlotsService;
    public final SensorService sensorService;
    private final PlotRepository repository;

    public PlotDto addPlot(PlotDto plotDto) {
        var entity = mapper.mapPlotDtoToEntity(plotDto);

        return mapper.mapPlotEntityToDto(repository.save(entity));
    }

    public PlotDto editPlot(PlotDto plotDto) {
        var plot = getById(plotDto.getId());
        if (plot != null) {
            return addPlot(plotDto);
        }
        throw new ElementNotFoundException("plot does not exist");
    }

    public Plot getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ElementNotFoundException(id + " id does not exist"));
    }

    public List<PlotDto> getAllPlots() {
        var plots = repository.findAll();

        return mapper.mapPlotEntityListToDtoList(plots);
    }

    /**
     * Method to add timeslots for a particular plot of land
     *
     * @param plotId
     * @param timeSlots
     * @return TimeSlots
     */
    public TimeSlotsDto configurePlotOfLand(Long plotId, TimeSlotsDto timeSlots) {
        var plot = getById(plotId);
        if (plot != null) {
            var entity = mapper.mapTimeSlotsDtoToEntity(timeSlots);
            entity.setPlot(plot);
            plot.setTimeSlots(Set.of(entity));

            editPlot(mapper.mapPlotEntityToDto(plot));
            return mapper.mapTimeSlotsEntityToDto(timeSlotsService.save(entity));
        } else throw new ElementNotFoundException("plot id does not exist");
    }

    private void triggerSensor(Long plotId, TimeSlotsDto timeSlots) {
        var response = sensorService.triggerSensor(plotId, timeSlots);
        if (response) {
            timeSlots.setStatus(StatusType.SUCCESS);
        } else timeSlots.setStatus(StatusType.UNSUCCESSFUL);

        timeSlotsService.save(mapper.mapTimeSlotsDtoToEntity(timeSlots));
    }

}
