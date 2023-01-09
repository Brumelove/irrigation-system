package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.dto.PlotDto;
import com.andela.irrigationsystem.dto.TimeSlotsDto;
import com.andela.irrigationsystem.exception.ElementNotFoundException;
import com.andela.irrigationsystem.mapper.IrrigationMapper;
import com.andela.irrigationsystem.model.Plot;
import com.andela.irrigationsystem.repositories.PlotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Brume
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class PlotService {
    public final IrrigationMapper mapper;
    public final TimeSlotsService timeSlotsService;
    public final SensorService sensorService;
    private final PlotRepository repository;

    /**
     * Create a plot of land
     *
     * @param plotDto
     * @return
     */
    public PlotDto addPlot(PlotDto plotDto) {
        var entity = mapper.mapPlotDtoToEntity(plotDto);

        return mapper.mapPlotEntityToDto(repository.save(entity));
    }

    /**
     * Edit a plot of land
     *
     * @param id
     * @param plotDto
     * @return PlotDto
     * @throws ElementNotFoundException
     */
    public PlotDto editPlot(Long id, PlotDto plotDto) {
        var plot = getById(id);
        if (plot != null) {
            plotDto.setId(id);
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

            triggerSensor(plotId, timeSlots);
            return mapper.mapTimeSlotsEntityToDto(timeSlotsService.save(entity));
        } else throw new ElementNotFoundException("plot id does not exist");
    }


    /**
     * method called to trigger sensor
     *
     * @param plotId
     * @param timeSlots
     */
    void triggerSensor(Long plotId, TimeSlotsDto timeSlots) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        sensorService.scheduleATask(plotId, timeSlots.getSensorNumber(),timeSlots);
    }

}
