package com.example.station.controllers;

import com.example.station.services.ObservatoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;

@RestController
public class ObservatoryController {
    private final ObservatoryService observatoryService;

    @Autowired
    public ObservatoryController(ObservatoryService observatoryService) {
        this.observatoryService = observatoryService;
    }

    @GetMapping("/observe")
    public Mono<String> observePlanets(@RequestParam String delay) {
        return observatoryService.observePlanets(delay);
    }

    @GetMapping("/test-backpressure")
    public void testBackPressure(@RequestParam String delay) {
        observatoryService.testBackPressure(delay);
    }

    @GetMapping("/test")
    public Flux<Integer> testBackPressure2() {
        observatoryService.testBackPressureClassic();

        return Flux.range(1, 10)
                .log();
    }
}
