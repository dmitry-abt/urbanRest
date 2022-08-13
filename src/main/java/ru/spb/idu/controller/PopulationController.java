package ru.spb.idu.controller;

import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.spb.idu.service.PopulationService;

import java.util.Map;

@RestController
public class PopulationController {
    @Autowired
    private PopulationService populationService;

    @PostMapping("/getPopulation")
    public Map<String, Long> getDistribution(@RequestBody Geometry boundaries) {
        return populationService.getDistribution(boundaries);
    }

}
