package ru.spb.idu.domain;

import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GeometricEntity extends AbstractEntity {

    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

}
