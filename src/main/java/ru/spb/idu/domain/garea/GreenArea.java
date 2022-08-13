package ru.spb.idu.domain.garea;

import ru.spb.idu.domain.GeometricEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "green_areas")
public class GreenArea extends GeometricEntity {

    private String name;
    private Integer type;
    private Integer purpose;
    private Boolean illumination;
    private Integer access;

    public GreenArea() {
    }

    public GreenArea(String name, Integer type, Integer purpose, Boolean illumination, Integer access) {
        this.name = name;
        this.type = type;
        this.purpose = purpose;
        this.illumination = illumination;
        this.access = access;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }

    public Boolean getIllumination() {
        return illumination;
    }

    public void setIllumination(Boolean illumination) {
        this.illumination = illumination;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    @Override
    public String toString() {
        return "GreenArea{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", purpose=" + purpose +
                ", illumination=" + illumination +
                ", access=" + access +
                '}';
    }

}
