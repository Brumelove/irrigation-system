package com.andela.irrigationsystem.mapper;

import com.andela.irrigationsystem.dto.PlotDto;
import com.andela.irrigationsystem.dto.SensorDto;
import com.andela.irrigationsystem.dto.TimeSlotsDto;
import com.andela.irrigationsystem.model.Plot;
import com.andela.irrigationsystem.model.Sensor;
import com.andela.irrigationsystem.model.TimeSlots;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Brume
 **/
@Mapper(componentModel = "spring")
public interface IrrigationMapper {
    static IrrigationMapper getIrrigationMapper() {
        return Mappers.getMapper(IrrigationMapper.class);
    }

    Plot mapPlotDtoToEntity(PlotDto plotDto);

    PlotDto mapPlotEntityToDto(Plot plot);

    List<PlotDto> mapPlotEntityListToDtoList(List<Plot> plots);

    TimeSlots mapTimeSlotsDtoToEntity(TimeSlotsDto timeSlotDto);

    TimeSlotsDto mapTimeSlotsEntityToDto(TimeSlots timeSlots);

    List<TimeSlotsDto> mapTimeSlotsEntityListToDtoList(List<TimeSlots> timeSlots);

    List<TimeSlotsDto> mapTimeSlotsDtoListToEntityList(List<TimeSlotsDto> timeSlots);

    Sensor mapSensorDtoToEntity(SensorDto sensorDto);

    SensorDto mapSensorEntityToDto(Sensor sensor);
}
