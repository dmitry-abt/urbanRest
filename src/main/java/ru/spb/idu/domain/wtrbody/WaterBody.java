package ru.spb.idu.domain.wtrbody;

import ru.spb.idu.domain.GeometricEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "water_bodies")
public class WaterBody extends GeometricEntity {

    private String name;

    public WaterBody() {
    }

    public WaterBody(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "WaterBody{" +
                "name='" + name + '\'' +
                '}';
    }

}
