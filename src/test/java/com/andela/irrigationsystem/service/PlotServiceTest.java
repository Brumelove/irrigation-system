package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.ObjectBuilder;
import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.mapper.IrrigationMapper;
import com.andela.irrigationsystem.repositories.PlotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * @author Brume
 **/
@ExtendWith(MockitoExtension.class)
class PlotServiceTest {
    @Mock
    public IrrigationMapper mapper;
    @Mock
    public TimeSlotsService timeSlotsService;
    @Mock
    public SensorService sensorService;
    @Mock
    private PlotRepository repository;
    @InjectMocks
    private PlotService plotService;

    @Test
    void addPlot() {
        var plot = ObjectBuilder.plot();
        var plotDto = ObjectBuilder.plotDto();

        when(mapper.mapPlotDtoToEntity(plotDto)).thenReturn(plot);
        when(mapper.mapPlotEntityToDto(plot)).thenReturn(plotDto);
        when(repository.save(plot)).thenReturn(plot);

        var response = plotService.addPlot(plotDto);

        assertNotNull(plot);
        assertEquals(1, plot.getId());
    }

    @Test
    void editPlot() {
        var plot = ObjectBuilder.plot();
        plot.setLocation("Lagos");
        plot.setOwnerName("Brume");

        var plotDto = ObjectBuilder.plotDto();
        plot.setLocation("Lagos");
        plot.setOwnerName("Brume");

        when(repository.findById(1L)).thenReturn(Optional.of(plot));

        when(mapper.mapPlotDtoToEntity(plotDto)).thenReturn(plot);
        when(mapper.mapPlotEntityToDto(plot)).thenReturn(plotDto);

        when(repository.save(plot)).thenReturn(plot);
        var response = plotService.editPlot(1l, plotDto);

        assertNotNull(response);
        assertEquals(1, response.getId());

        verify(repository, times(1)).save(mapper.mapPlotDtoToEntity(response));
    }

    @Test
    void getById() {
        var plot = ObjectBuilder.plot();

        when(repository.findById(1L)).thenReturn(Optional.of(plot));
        var response = plotService.getById(1L);

        assertNotNull(response);
        assertEquals(1, response.getId());

    }

    @Test
    void getAllPlots() {
        var plot = ObjectBuilder.plot();

        when(mapper.mapPlotEntityListToDtoList(List.of(plot))).thenReturn(List.of(ObjectBuilder.plotDto()));
        when(repository.findAll()).thenReturn(List.of(plot));
        var response = plotService.getAllPlots();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertNull(response.get(0).getTimeSlots());
    }

    @Test
    void configurePlotOfLand() throws IOException {
        var plot = ObjectBuilder.plot();
        var timeSlotsDto = ObjectBuilder.timeSlotsDto(FrequencyType.HOURLY, 2, 0);
        var timeSlots = ObjectBuilder.timeSlot(FrequencyType.HOURLY, 2, 0);

        when(repository.findById(1L)).thenReturn(Optional.of(plot));

        when(timeSlotsService.save(timeSlotsDto)).thenReturn(timeSlotsDto);
        var response = plotService.configurePlotOfLand(1L, timeSlotsDto);

        assertNotNull(plot);
        assertEquals(1, response.getId());
        assertEquals("hourly", response.getFrequency().name().toLowerCase());
    }



}