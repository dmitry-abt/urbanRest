package ru.spb.idu.domain.block;

import ru.spb.idu.domain.GeometricEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "blocks")
public class Block extends GeometricEntity {

    private String name;
    private Boolean illumination;

    public Block() {
    }

    public Block(String name, Boolean illumination) {
        this.name = name;
        this.illumination = illumination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIllumination() {
        return illumination;
    }

    public void setIllumination(Boolean illumination) {
        this.illumination = illumination;
    }

    @Override
    public String toString() {
        return "Block{" +
                "name='" + name + '\'' +
                ", illumination=" + illumination +
                '}';
    }

}
