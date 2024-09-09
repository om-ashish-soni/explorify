package com.learn.explorify.controller;

import com.learn.explorify.client.WeatherClient;
import com.learn.explorify.model.Event;
import com.learn.explorify.model.Registration;
import com.learn.explorify.model.Weather;
import com.learn.explorify.repository.EventRepository;
import com.learn.explorify.repository.RegistrationRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
    private final EventRepository eventRepository;
    private final RegistrationRepository registrationRepository;
    private final WeatherClient weatherClient;
    private final WebClient webClient;

    @PostConstruct
    public void init(){
        System.out.println("In the init : "+Thread.currentThread().getName());
        List<CompletableFuture<Weather>> futures=new ArrayList<>();
        for(int i=1;i<=10;i++){
            futures.add(getWeatherAsync(i));
        }
        futures.stream().map(CompletableFuture::join).forEach(
                arg->{
                    System.out.println("arg : "+arg.getTemperature()+" : "+Thread.currentThread().getName());
                }
        );
        System.out.println("Out of the init : "+Thread.currentThread().getName());
    }



    @PostMapping("/create")
    public Event create(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    @PostMapping("/register")
    public Registration create(@RequestBody Registration registration) {
        return registrationRepository.save(registration);
    }

    @GetMapping("/all-registrations")
    public List<Registration> get(@RequestBody Registration registration) {
        List<Registration> registrations = registrationRepository.findAll();
        return registrations;
    }

    private Mono<Weather> getWeather(int identity) {
        System.out.println("before calling getWeather : " + identity + " thread : " + Thread.currentThread().getName());

        Mono<Weather> weatherMono = Mono.just(
                weatherClient.getWeather()
        );

        System.out.println("after calling getWeather : " + identity + " thread : " + Thread.currentThread().getName());

        return weatherMono;
    }

    private CompletableFuture<Weather> getWeatherAsync(int identity){
        return getWeatherFlux(identity).toFuture();
    }
    private Mono<Weather> getWeatherFlux(int identity) {
        System.out.println("before calling getWeatherFlux : " + identity + " thread : " + Thread.currentThread().getName());

        Mono<Weather> weatherMono = webClient.get()
                .uri("/weather/get")
                .retrieve()
                .bodyToMono(Weather.class);

        System.out.println("after calling getWeatherFlux : " + identity + " thread : " + Thread.currentThread().getName());

        return weatherMono;
    }

    @GetMapping("/flux")
    public Mono<Map<String, Weather>> getFlux() {
        System.out.println("in getFlux : " + Thread.currentThread().getName());
        System.out.println("before calling getWeather" + Thread.currentThread().getName());
        Flux<Weather> weatherFlux = Flux.fromStream(
                IntStream.rangeClosed(1, 10).mapToObj(this::getWeather)
        ).flatMap(mono -> mono);
        System.out.println("After calling getWeather" + Thread.currentThread().getName());
        Mono<Map<String, Weather>> monoMap = weatherFlux.collectList()
                .map(list -> IntStream.range(0, list.size()).boxed().collect(Collectors.toMap(i -> "Weather:" + (i + 1), list::get)));
        System.out.println("After converting toMap" + Thread.currentThread().getName());
        return monoMap;
    }

    @GetMapping("/sync")
    public Map<String, Weather> getSync() {
        System.out.println("in getFlux : " + Thread.currentThread().getName());
        Map<String, Weather> map = new HashMap<>();
        System.out.println("before calling getWeather" + Thread.currentThread().getName());
        IntStream.rangeClosed(1, 10).forEach(i -> map.put("Weather:" + i, this.getWeather(i).block()));
        System.out.println("After calling getWeather" + Thread.currentThread().getName());
        return map;
    }
}
