package com.epam.spring.webflux.demo.handler;

import com.epam.spring.webflux.demo.domain.Identity;
import com.epam.spring.webflux.demo.domain.Vehicle;
import com.epam.spring.webflux.demo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class VehicleHandler {

    private final VehicleRepository repository;

    @Autowired
    public VehicleHandler(VehicleRepository repository) {
        this.repository = repository;
    }

    public Mono<ServerResponse> listVehicles(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findAll(), Vehicle.class);
    }

    public Mono<ServerResponse> createVehicle(ServerRequest request) {
        return request.bodyToMono(Vehicle.class)
                .flatMap(repository::save)
                .flatMap(vehicle -> ServerResponse.ok().body(fromObject(new Identity(vehicle.getVin()))))
                .onErrorResume(throwable -> ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> findVehicle(ServerRequest request) {
        String personId = request.pathVariable("vin");
        return repository.findById(personId)
                .flatMap(vehicle -> ServerResponse.ok().body(fromObject(vehicle)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getVehicleByModel(ServerRequest request) {
        String model = request.pathVariable("model");
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findByModel(model), Vehicle.class);
    }
}