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

        return repository.save(timeSlots);
    }

}
