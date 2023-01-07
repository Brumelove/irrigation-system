package com.andela.irrigationsystem.repositories;

import com.andela.irrigationsystem.model.Plot;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Brume
 **/
public interface PlotRepository extends JpaRepository<Plot, Long> {

}
