package com.darlan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Service
@SuppressWarnings("unused")
public class PersonHandler {

    private static final Logger log = LoggerFactory.getLogger(PersonHandler.class);

    @Autowired
    private PersonRepository personRepository;

    public PersonHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    Mono<ServerResponse> list(ServerRequest req) {
        log.info("Received request: {} -> {}", req.method(), req.uri());
        return ServerResponse.ok()
                .body(personRepository.findAll(), Person.class)
                .onErrorResume(throwable -> ServerResponse
                        .status(INTERNAL_SERVER_ERROR)
                        .build());
    }

    Mono<ServerResponse> create(ServerRequest req) {
        log.info("Received request: {} -> {}", req.method(), req.uri());
        return req.bodyToMono(Person.class)
                .flatMap(person -> personRepository.save(person))
                .flatMap(person -> ServerResponse.ok()
                        .body(fromObject(person)))
                .onErrorResume(throwable -> ServerResponse
                        .status(INTERNAL_SERVER_ERROR)
                        .build());
    }

    Mono<ServerResponse> search(ServerRequest req) {
        log.info("Received request: {} -> {}", req.method(), req.uri());
        String id = req.pathVariable("id");
        Mono<Person> personMono = personRepository.findById(id);
        return personMono
                .flatMap(person -> ServerResponse.ok()
                        .body(fromObject(person)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    Mono<ServerResponse> update(ServerRequest req) {
        log.info("Received request: {} -> {}", req.method(), req.uri());
        String id = req.pathVariable("id");
        Mono<Boolean> personMono = personRepository.existsById(id);
        return personMono
                .flatMap(exists -> exists ? req.bodyToMono(Person.class) : Mono.empty())
                .flatMap(person -> {
                    person.setId(id);
                    return personRepository.save(person);
                })
                .flatMap(person -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(fromObject(person)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    Mono<ServerResponse> delete(ServerRequest req) {
        log.info("Received request: {} -> {}", req.method(), req.uri());
        String id = req.pathVariable("id");
        Mono<Void> personMono = personRepository.deleteById(id);
        return personMono
                .flatMap(v -> ServerResponse.noContent().build())
                .onErrorResume(throwable -> ServerResponse
                        .status(INTERNAL_SERVER_ERROR)
                        .build());
    }

}
