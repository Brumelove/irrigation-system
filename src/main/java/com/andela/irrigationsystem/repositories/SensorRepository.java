package com.andela.irrigationsystem.repositories;

import com.andela.irrigationsystem.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Brume
 **/
public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
