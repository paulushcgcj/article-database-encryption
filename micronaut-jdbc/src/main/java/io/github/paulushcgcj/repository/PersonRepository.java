package io.github.paulushcgcj.repository;

import io.github.paulushcgcj.entity.PersonEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

}
