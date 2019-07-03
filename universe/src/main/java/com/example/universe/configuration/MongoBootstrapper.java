package com.example.universe.configuration;

import com.example.universe.models.Info;
import com.example.universe.models.Planets;
import com.example.universe.repository.PlanetsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class MongoBootstrapper {
    @Bean
    CommandLineRunner init(ReactiveMongoOperations operations, PlanetsRepository repository) {
        return args -> {
            Flux<Planets> planetsFlux = Flux.just(
                    new Planets(null, "Earth", new Info("6 x 10^24kg", "40075km")),
                    new Planets(null, "Mars", new Info("6.39 x 10^23kg", "6794km")),
                    new Planets(null, "Mercury", new Info("3.3 x 10^23kg", "4879.4")),
                    new Planets(null, "Venus", new Info("4.9 x 10^24kg", "12,104km")),
                    new Planets(null, "Jupiter", new Info("1.9 x 10^27kg", "139,820km")),
                    new Planets(null, "Saturn", new Info("5.7 x 10^26kg", "116,460km")),
                    new Planets(null, "Uranus", new Info("8.7 x 10^25kg", "50,724km")),
                    new Planets(null, "Neptune", new Info("1 x 10^26kg", "49,245km")))
                    .flatMap(repository::save);

//            planetsFlux.thenMany(repository.findAll())
//                    .subscribe(System.out::println);

            planetsFlux.thenMany(repository.findAll())
                    .subscribe(
                            System.out::println,
                            Throwable::printStackTrace,
                            () -> System.out.println("completed without a value")
                    );

//            System.out.println("hehe haha: " + planetsFlux.collectList());

//            operations.collectionExists(Planets.class)
//                    .flatMap(exists -> exists ? operations.dropCollection(Planets.class) : Mono.just(exists))
//                    .thenMany(it -> operations.createCollection(Planets.class))
//                    .thenMany(planetsFlux)
//                    .thenMany(repository.findAll())
//                    .subscribe(System.out::println);
        };
    }
}
