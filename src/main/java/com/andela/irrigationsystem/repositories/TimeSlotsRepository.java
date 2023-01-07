package com.andela.irrigationsystem.repositories;

import com.andela.irrigationsystem.model.TimeSlots;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Brume
 **/
public interface TimeSlotsRepository extends JpaRepository<TimeSlots, Long> {

    List<TimeSlots> findByPlot_Id(Long plotId);
}
