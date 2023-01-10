package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.dto.TimeSlotsDto;
import com.andela.irrigationsystem.exception.ElementNotFoundException;
import com.andela.irrigationsystem.mapper.IrrigationMapper;
import com.andela.irrigationsystem.repositories.TimeSlotsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Brume
 **/
@Service
@RequiredArgsConstructor
public class TimeSlotsService {
    private final TimeSlotsRepository repository;
    private final IrrigationMapper mapper;


    public TimeSlotsDto save(TimeSlotsDto timeSlotsDto) {
        var timeSlotsEntity = mapper.mapTimeSlotsDtoToEntity(timeSlotsDto);


        return mapper.mapTimeSlotsEntityToDto(repository.save(timeSlotsEntity));


    }

    public TimeSlotsDto update(TimeSlotsDto timeSlots) {
        System.out.println(timeSlots.getId());
        var timeslot = repository.findById(timeSlots.getId());
        if (timeslot.isPresent()) {
            TimeSlotsDto timeSlotsDto = new TimeSlotsDto();
            timeSlotsDto.setId(timeSlots.getId());
            timeSlotsDto.setDayValue(timeSlots.getDayValue());
            timeSlotsDto.setTimeValue(timeSlots.getTimeValue());
            timeSlotsDto.setFrequency(timeSlots.getFrequency());
            timeSlotsDto.setStatus(timeSlots.getStatus());
            timeSlotsDto.setCubicWaterAmount(timeSlots.getCubicWaterAmount());
            timeSlotsDto.setSensorNumber(timeSlots.getSensorNumber());
            timeSlotsDto.setPlot(timeSlots.getPlot());

            return save(timeSlotsDto);
        } else throw new ElementNotFoundException("id does not exist");
    }


}
