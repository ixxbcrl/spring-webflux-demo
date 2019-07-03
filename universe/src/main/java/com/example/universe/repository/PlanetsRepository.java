package com.example.universe.repository;

import com.example.universe.models.Planets;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetsRepository extends ReactiveMongoRepository<Planets, String> {

}
