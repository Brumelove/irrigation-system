package com.andela.irrigationsystem.model;

import com.andela.irrigationsystem.enumerations.FrequencyType;
import com.andela.irrigationsystem.enumerations.StatusType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    private int dayValue;
    private int timeValue;
    private String sensorNumber;
    private Double cubicWaterAmount;
    @Enumerated(EnumType.STRING)
    private StatusType status;
    @Enumerated(EnumType.STRING)
    private FrequencyType frequency;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "configure_plot",
            joinColumns = {@JoinColumn(name = "time_slot_id")},
            inverseJoinColumns = {@JoinColumn(name = "plot_id")})
    private Plot plot;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlots timeSlots)) return false;
        return getDayValue() == timeSlots.getDayValue() && getTimeValue() == timeSlots.getTimeValue() && getId().equals(timeSlots.getId()) && getSensorNumber().equals(timeSlots.getSensorNumber()) && getCubicWaterAmount().equals(timeSlots.getCubicWaterAmount()) && getStatus() == timeSlots.getStatus() && getFrequency() == timeSlots.getFrequency() && getPlot().equals(timeSlots.getPlot());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
