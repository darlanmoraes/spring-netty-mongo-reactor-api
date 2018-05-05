package com.darlan;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface PersonRepository extends ReactiveCrudRepository<Person, String> { }
