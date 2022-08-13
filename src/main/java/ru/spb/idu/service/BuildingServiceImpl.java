package ru.spb.idu.service;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.spb.idu.domain.building.Building;
import ru.spb.idu.repository.BuildingRepository;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public List<Building> findWithin(Geometry filter) {
        return buildingRepository.findWithin(filter);
    }

    @Override
    public List<Building> findIntersect(Geometry filter) {
        return buildingRepository.findIntersect(filter);
    }

    @Override
    public List<Building> findNear(Geometry filter, Integer radius) {
        return buildingRepository.findNear(filter, radius);
    }
}
