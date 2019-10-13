package com.example.universe;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class FluxTest {

    @Test
    public void fluxTestSubscribe() {
        Flux.range(1, 10)
                .log()
        .subscribe();
    }

    @Test
    public void fluxTestSubscriber() throws InterruptedException {
        Flux.just("a", "b", "c") //this is where subscription triggers data production
                //this is influenced by subscribeOn
                .log()
                .doOnNext(v -> System.out.println("before publishOn: " + Thread.currentThread().getName()))
                .publishOn(Schedulers.elastic())
                //the rest is influenced by publishOn
                .log()
                .doOnNext(v -> System.out.println("after publishOn: " + Thread.currentThread().getName()))
                .subscribeOn(Schedulers.parallel())
                .subscribe(v -> System.out.println("received " + v + " on " + Thread.currentThread().getName()));
        Thread.sleep(5000);
    }
}
