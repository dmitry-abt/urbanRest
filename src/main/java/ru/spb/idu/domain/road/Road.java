package ru.spb.idu.domain.road;

import ru.spb.idu.domain.GeometricEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "roads")
public class Road extends GeometricEntity {

    private String name;
    private Integer type;
    private Boolean illumination;
    private Integer traffic;
    private Integer accidents;
    private Integer profile;
    private Integer surface;

    public Road() {
    }

    public Road(String name, Integer type, Boolean illumination, Integer traffic, Integer accidents, Integer profile, Integer surface) {
        this.name = name;
        this.type = type;
        this.illumination = illumination;
        this.traffic = traffic;
        this.accidents = accidents;
        this.profile = profile;
        this.surface = surface;
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

    public Boolean getIllumination() {
        return illumination;
    }

    public void setIllumination(Boolean illumination) {
        this.illumination = illumination;
    }

    public Integer getTraffic() {
        return traffic;
    }

    public void setTraffic(Integer traffic) {
        this.traffic = traffic;
    }

    public Integer getAccidents() {
        return accidents;
    }

    public void setAccidents(Integer accidents) {
        this.accidents = accidents;
    }

    public Integer getProfile() {
        return profile;
    }

    public void setProfile(Integer profile) {
        this.profile = profile;
    }

    public Integer getSurface() {
        return surface;
    }

    public void setSurface(Integer surface) {
        this.surface = surface;
    }

    @Override
    public String toString() {
        return "Road{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", illumination=" + illumination +
                ", traffic=" + traffic +
                ", accidents=" + accidents +
                ", profile=" + profile +
                ", surface=" + surface +
                '}';
    }

}
