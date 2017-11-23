package com.epam.spring.webflux.demo.repository;

import com.epam.spring.webflux.demo.domain.Vehicle;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface VehicleRepository extends ReactiveCrudRepository<Vehicle, String> {

    Flux<Vehicle> findByModel(String model);

}
