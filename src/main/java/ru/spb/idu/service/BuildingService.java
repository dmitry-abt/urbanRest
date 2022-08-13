package ru.spb.idu.service;

import org.locationtech.jts.geom.Geometry;
import ru.spb.idu.domain.building.Building;

import java.util.List;

public interface BuildingService {
    List<Building> findWithin(Geometry filter);

    List<Building> findIntersect(Geometry filter);

    List<Building> findNear(Geometry filter, Integer radius);
}
