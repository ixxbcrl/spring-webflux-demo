package com.example.stationblocking.controllers;

import com.example.stationblocking.services.ObservatoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ObservatoryController {
    private final ObservatoryService observatoryService;

    @Autowired
    public ObservatoryController(ObservatoryService observatoryService) {
        this.observatoryService = observatoryService;
    }

    @GetMapping("/observe")
    public String observePlanets(@RequestParam String delay) {
        return observatoryService.observePlanets(delay);
    }
}
