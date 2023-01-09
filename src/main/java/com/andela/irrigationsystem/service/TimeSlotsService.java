package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.model.TimeSlots;
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


    public TimeSlots save(TimeSlots timeSlots) {
        var timeslot = repository.findById(timeSlots.getId());
        if (timeslot.isPresent()) {
            var timeSlotEntity = new TimeSlots();
            timeSlotEntity.setId(timeSlots.getId());
            timeSlotEntity.setDayValue(timeSlots.getDayValue());
            timeSlotEntity.setTimeValue(timeSlots.getTimeValue());
            timeSlotEntity.setFrequency(timeSlots.getFrequency());
            timeSlotEntity.setCubicWaterAmount(timeSlots.getCubicWaterAmount());
            timeSlotEntity.setSensorNumber(timeSlots.getSensorNumber());
            timeSlotEntity.setPlot(timeSlots.getPlot());

            return repository.save(timeSlots);
        }

        return repository.save(timeSlots);
    }



}
