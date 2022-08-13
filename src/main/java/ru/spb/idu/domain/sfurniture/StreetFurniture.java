package ru.spb.idu.domain.sfurniture;

import ru.spb.idu.domain.GeometricEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "street_furniture")
@Inheritance(strategy = InheritanceType.JOINED)
public class StreetFurniture extends GeometricEntity {

    private Integer type;

    public StreetFurniture(Integer type) {
        this.type = type;
    }

    public StreetFurniture() {
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StreetFurniture{" +
                "type=" + type +
                '}';
    }

}
