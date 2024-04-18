package io.github.paulushcgcj.repository;

import io.github.paulushcgcj.entity.PersonEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.reactive.ReactorPageableRepository;

@Repository
public interface PersonRepository extends ReactorPageableRepository<PersonEntity, Long> {

}
