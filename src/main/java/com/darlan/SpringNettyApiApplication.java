package com.darlan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@SpringBootApplication
@SuppressWarnings("unused")
public class SpringNettyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringNettyApiApplication.class, args);
	}

	@Bean
	public RouterFunction<?> personRoutes(final PersonHandler personHandler) {
		return RouterFunctions.route(
					  GET("/persons"), personHandler::list)
			.andRoute(POST("/persons"), personHandler::create)
			.andRoute(GET("/persons/{id}"), personHandler::search)
			.andRoute(PUT("/persons/{id}"), personHandler::update)
			.andRoute(DELETE("/persons/{id}"), personHandler::delete);
	}

}
