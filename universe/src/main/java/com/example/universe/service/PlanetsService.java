package com.example.universe.service;

import com.example.universe.models.Planets;
import com.example.universe.repository.PlanetsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class PlanetsService {
    PlanetsRepository planetsRepository;

    public PlanetsService(PlanetsRepository planetsRepository) {
        this.planetsRepository = planetsRepository;
    }

    public Flux<Planets> getAllPlanetNames() {
        return planetsRepository.findAll();
    }
}
