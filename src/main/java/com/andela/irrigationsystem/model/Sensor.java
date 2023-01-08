package com.andela.irrigationsystem.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Brume
 **/
@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"plotId", "sensorNumber"})})
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sensorNumber;
    private Long plotId;
    private String sensorsEndpoint;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Sensor plot = (Sensor) o;
        return id != null && Objects.equals(id, plot.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
