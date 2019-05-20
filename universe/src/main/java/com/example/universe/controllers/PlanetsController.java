package com.example.universe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PlanetsController {
    @GetMapping("/planets")
    public Mono<String> getPlanetName(@RequestParam long delay) {
        return Mono.just(getRandomPlanetName())
                .delayElement(Duration.ofMillis(delay));
    }

    public String getRandomPlanetName() {
        Random rand = new Random();
        List<String> list = Stream.of(
                "Mercury",
                "Venus",
                "Earth",
                "Mars",
                "Jupiter",
                "Saturn",
                "Uranus",
                "Neptune")
                .collect(Collectors.toList());
        return list.get(rand.nextInt(list.size()));
    }
}
