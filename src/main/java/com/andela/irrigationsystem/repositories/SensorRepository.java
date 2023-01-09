package com.andela.irrigationsystem.repositories;

import com.andela.irrigationsystem.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author Brume
 **/
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query("select s.sensorsEndpoint from Sensor s where s.sensorNumber =:sensorNumber and s.plotId =:plotId")
    Optional<String> getSensorEndpointBySensorNumberAndPlotId(String sensorNumber, Long plotId);
}
