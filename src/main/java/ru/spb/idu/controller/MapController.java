package ru.spb.idu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MapController {
    @RequestMapping("/")
    public String root() {
        return "redirect:/map";
    }

    @RequestMapping("/map")
    public String index() {
        return "map";
    }

}
