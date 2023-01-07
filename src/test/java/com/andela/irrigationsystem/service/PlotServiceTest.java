package com.andela.irrigationsystem.service;

import com.andela.irrigationsystem.model.Plot;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * @author Brume
 **/
@ExtendWith(MockitoExtension.class)
class PlotServiceTest {

    @Test
    void addPlot() {

    }

    @Test
    void editPlot() {
    }

    @Test
    void getById() {
    }

    @Test
    void getAllPlots() {
    }

    @Test
    void configurePlotOfLand() {
    }

    private Plot plot() {
        var plot = new Plot();
        plot.setId(RandomUtils.nextLong());
        plot.setSize(RandomUtils.nextLong());
        plot.setCropType(RandomStringUtils.randomAlphanumeric(4));
        plot.setCropName(RandomStringUtils.randomAlphanumeric(4));

    }
}