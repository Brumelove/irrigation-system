package com.andela.irrigationsystem.model;

import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.enumerations.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Brume
 **/
@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class TimeSlots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int weekDay;
    private int timeValue;
    private Double cubicWaterAmount;
    private StatusType status;
    private FrequencyType frequency;
    private int numberOfRetries;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "configure_plot",
            joinColumns = {@JoinColumn(name = "time_slot_id")},
            inverseJoinColumns = {@JoinColumn(name = "plot_id")})
    private Plot plot;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlots timeSlots)) return false;
        return getWeekDay() == timeSlots.getWeekDay() && getTimeValue() == timeSlots.getTimeValue() && getNumberOfRetries() == timeSlots.getNumberOfRetries() && getId().equals(timeSlots.getId()) && getCubicWaterAmount().equals(timeSlots.getCubicWaterAmount()) && getStatus() == timeSlots.getStatus() && getFrequency() == timeSlots.getFrequency() && getPlot().equals(timeSlots.getPlot());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
