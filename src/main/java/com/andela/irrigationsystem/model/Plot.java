package com.andela.irrigationsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;
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
    private Long size;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL)
    private Set<TimeSlots> timeSlots;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Plot plot = (Plot) o;
        return id != null && Objects.equals(id, plot.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
