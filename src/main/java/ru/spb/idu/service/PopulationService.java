package ru.spb.idu.service;

import org.locationtech.jts.geom.Geometry;

import java.util.Map;

public interface PopulationService {
    Map<String, Long> getDistribution(Geometry filter);

}
