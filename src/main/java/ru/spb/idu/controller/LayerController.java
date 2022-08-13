package ru.spb.idu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.spb.idu.domain.layer.Layer;
import ru.spb.idu.service.LayerService;

import java.util.List;

@RestController
public class LayerController {
    @Autowired
    private LayerService layerService;

    @GetMapping("/getLayer")
    public Layer getLayer(@RequestParam Long id) {
        return layerService.findById(id).get();
    }

    @GetMapping("/findLayers")
    public List<Layer> findLayers(@RequestParam String startsWith, @RequestParam Integer maxRows) {
        return layerService.findByNameStartingWith(startsWith, maxRows);
    }

}
