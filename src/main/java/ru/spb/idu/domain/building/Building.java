package ru.spb.idu.domain.building;

import ru.spb.idu.domain.GeometricEntity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "buildings")
@Inheritance(strategy = InheritanceType.JOINED)
public class Building extends GeometricEntity {

    private String name;
    private String address;
    private Integer height;
    private Integer floors;

//    @Column(name = "failure")
//    private Integer failure;

    @Column(name="year_construct")
    private Date yearConstuct;

    @Column(name="year_repair")
    private Date yearRepair;

    public Building() {
    }

    public Building(String name, String address, Integer height, Integer floors, Date yearConstuct, Date yearRepair/*, Integer failure*/) {
        this.name = name;
        this.address = address;
        this.height = height;
        this.floors = floors;
        this.yearConstuct = yearConstuct;
        this.yearRepair = yearRepair;
//        this.failure = failure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public Date getYearConstuct() {
        return yearConstuct;
    }

    public void setYearConstuct(Date yearConstuct) {
        this.yearConstuct = yearConstuct;
    }

    public Date getYearRepair() {
        return yearRepair;
    }

    public void setYearRepair(Date yearRepair) {
        this.yearRepair = yearRepair;
    }

//    public Integer getFailure() { return failure; }
//
//    public void setFailure(Integer failure) { this.failure = failure; }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + getId() + '\'' +
                ", geometry=" + getGeometry() + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", height=" + height +
                ", floors=" + floors +
                ", yearConstuct=" + yearConstuct +
                ", yearRepair=" + yearRepair +
//                ", failure=" + failure +
                '}';
    }

}
