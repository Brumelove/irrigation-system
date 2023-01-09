package com.andela.irrigationsystem;

import com.andela.irrigationsystem.dto.PlotDto;
import com.andela.irrigationsystem.dto.TimeSlotsDto;
import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.enumerations.StatusType;
import com.andela.irrigationsystem.model.Plot;
import com.andela.irrigationsystem.model.TimeSlots;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

/**
 * @author Brume
 **/
public class ObjectBuilder {

    public static TimeSlots timeSlot(FrequencyType frequencyType, int timeValue, int day) {
        var timeSlots = new TimeSlots();
        timeSlots.setId(1L);
        timeSlots.setFrequency(frequencyType);
        timeSlots.setStatus(StatusType.PENDING);
        timeSlots.setTimeValue(timeValue);
        timeSlots.setCubicWaterAmount(RandomUtils.nextDouble());
        timeSlots.setSensorNumber(RandomStringUtils.randomAlphanumeric(7));
        timeSlots.setDayValue(day);

        return timeSlots;
    }

    public static TimeSlotsDto timeSlotsDto(FrequencyType frequencyType, int timeValue, int day) {
        var timeSlots = new TimeSlotsDto();
        timeSlots.setId(1L);
        timeSlots.setFrequency(frequencyType);
        timeSlots.setStatus(StatusType.PENDING);
        timeSlots.setTimeValue(timeValue);
        timeSlots.setCubicWaterAmount(RandomUtils.nextDouble());
        timeSlots.setSensorNumber(RandomStringUtils.randomAlphanumeric(7));
        timeSlots.setDayValue(day);

        return timeSlots;
    }

    public static Plot plot() {
        Plot plot = new Plot();
        plot.setId(1L);
        plot.setSizeInMeters(RandomUtils.nextLong());
        plot.setCropType(RandomStringUtils.randomAlphanumeric(4));
        plot.setCropName(RandomStringUtils.randomAlphanumeric(4));
        plot.setLocation(RandomStringUtils.randomAlphanumeric(4));
        plot.setOwnerName(RandomStringUtils.randomAlphanumeric(4));
        return plot;
    }

    public static PlotDto plotDto() {
        PlotDto plot = new PlotDto();
        plot.setId(1L);
        plot.setSizeInMeters(RandomUtils.nextLong());
        plot.setCropType(RandomStringUtils.randomAlphanumeric(4));
        plot.setCropName(RandomStringUtils.randomAlphanumeric(4));
        plot.setLocation(RandomStringUtils.randomAlphanumeric(4));
        plot.setOwnerName(RandomStringUtils.randomAlphanumeric(4));

        return plot;
    }

}
