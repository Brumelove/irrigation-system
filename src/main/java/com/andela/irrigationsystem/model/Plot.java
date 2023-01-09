package com.andela.irrigationsystem.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Brume
 **/
@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class Plot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ownerName;
    private String location;
    private String cropType;
    private String cropName;
    private Long sizeInMeters;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<TimeSlots> timeSlots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plot plot)) return false;
        return getId().equals(plot.getId()) && getOwnerName().equals(plot.getOwnerName()) && getLocation().equals(plot.getLocation()) && getCropType().equals(plot.getCropType()) && getCropName().equals(plot.getCropName()) && getSizeInMeters().equals(plot.getSizeInMeters()) && getTimeSlots().equals(plot.getTimeSlots());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
