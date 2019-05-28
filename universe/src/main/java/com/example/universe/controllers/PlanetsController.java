package com.example.universe.controllers;

import com.example.universe.controllers.models.Delay;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class PlanetsController {
    @PostMapping("/planets")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> getPlanetName(@RequestBody Mono<Delay> delay) {
        return delay.flatMap(p -> Mono.just(getRandomPlanetName())
                .delayElement(Duration.ofMillis(Long.parseLong(p.getDelayTime()))));
//        return Mono.just(getRandomPlanetName())
//                .delayElement(Duration.ofMillis(50));
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
