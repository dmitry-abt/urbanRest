package ru.spb.idu.controller;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.spb.idu.domain.GeometricEntity;
import ru.spb.idu.service.GeometricEntityService;

import java.util.List;

@RestController
public class GeometricEntityController {
    @Autowired
    private GeometricEntityService service;

    @PostMapping("/findIntersect")
    public List<GeometricEntity> findIntersect(@RequestBody Geometry boundaries) {
        return service.findIntersect(boundaries);
    }

    @PostMapping("/findPedestrianGraph")
    public List<GeometricEntity> findPedestrianGraph(@RequestBody Data data) {
        return service.findPedestrianGraph(data.geometry, data.radius);
    }

    @PostMapping("/findPedestrianConcaveHull")
    public List<GeometricEntity> findPedestrianConcaveHull(@RequestBody Data data) {
        return service.findPedestrianConcaveHull(data.geometry, data.radius);
    }

    @PostMapping("/findCarGraph")
    public List<GeometricEntity> findCarGraph(@RequestBody Data data) {
        return service.findCarGraph(data.geometry, data.radius * 1000);
    }

    @PostMapping("/findCarConcaveHull")
    public List<GeometricEntity> findCarConcaveHull(@RequestBody Data data) {
        return service.findCarConcaveHull(data.geometry, data.radius * 1000);
    }

    @PostMapping("/findNear")
    public List<GeometricEntity> findNear(@RequestBody Data data) {
        return service.findNear(data.geometry, data.radius);
    }
}

class Data
{
    public Geometry geometry;
    public Integer radius;
}