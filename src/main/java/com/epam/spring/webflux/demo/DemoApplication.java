package com.epam.spring.webflux.demo;

import com.epam.spring.webflux.demo.handler.EchoHandler;
import com.epam.spring.webflux.demo.handler.VehicleHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(DemoApplication.class);
    }

    @Bean
    public RouterFunction<ServerResponse> echoRouterFunction(EchoHandler echoHandler) {
        return route(POST("/echo"), echoHandler::echo);
    }

    @Bean
    public RouterFunction<ServerResponse> vehicleRouterFunction(VehicleHandler vehicleHandler) {
        return route(
                POST("/vehicle"), vehicleHandler::createVehicle).andRoute(
                GET("/vehicle"), vehicleHandler::listVehicles).andRoute(
                GET("/vehicle/{vin}"), vehicleHandler::findVehicle).andRoute(
            GET("/vehicle/model/{model}"), vehicleHandler::getVehicleByModel).andRoute(
            POST("/vehicle/dummy"), vehicleHandler::createDummyData);
    }

}