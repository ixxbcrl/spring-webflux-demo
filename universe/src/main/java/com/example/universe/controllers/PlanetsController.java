package com.example.universe.controllers;

import com.example.universe.models.Delay;
import com.example.universe.models.Planet;
import com.example.universe.models.Planets;
import com.example.universe.service.PlanetsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/planets")
public class PlanetsController {
    private PlanetsService planetsService;

    public PlanetsController(PlanetsService planetsService) {
        this.planetsService = planetsService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<String> getPlanetName(@RequestBody Mono<Delay> delay) {
        return delay.flatMap(p -> Mono.just(getRandomPlanetName())
                .delayElement(Duration.ofMillis(Long.parseLong(p.getDelayTime()))));
//        return Mono.just(getRandomPlanetName())
//                .delayElement(Duration.ofMillis(50));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<Planets> getAllPlanets() {
        return planetsService.getAllPlanetNames();
    }

    @GetMapping(path = "/sse-all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Flux<String> getAllPlanetsSSE() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> "Flux - " + LocalTime.now().toString());
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> testEndpoint() {
        System.out.println("start123");
        planetsService.testSubscribe();
        return planetsService.getAllPlanetNames()
                .then();
    }

    @GetMapping("/backpressure")
    public ResponseEntity<Flux<Integer>> testBackpressure() {
        return ResponseEntity.ok(Flux.range(1, 10000)
                .log()
//                .limitRate(10)
        );
    }

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public Flux<Planet> getAllPlanets() {
//        return planetService.retrieveAll()
//                .limitRate(10);
//    }

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
