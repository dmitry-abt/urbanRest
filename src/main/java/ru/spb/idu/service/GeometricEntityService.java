package ru.spb.idu.service;

import org.locationtech.jts.geom.Geometry;
import ru.spb.idu.domain.GeometricEntity;

import java.util.List;

public interface GeometricEntityService {
    List<GeometricEntity> findWithin(Geometry filter);

    List<GeometricEntity> findIntersect(Geometry filter);

    List<GeometricEntity> findPedestrianGraph(Geometry filter, Integer radius);

    List<GeometricEntity> findPedestrianConcaveHull(Geometry filter, Integer radius);

    List<GeometricEntity> findCarGraph(Geometry filter, Integer radius);

    List<GeometricEntity> findCarConcaveHull(Geometry filter, Integer radius);

    List<GeometricEntity> findNear(Geometry filter, Integer radius);
}
