package io.github.paulushcgcj.service;

import io.github.paulushcgcj.dto.PersonDto;
import io.github.paulushcgcj.entity.PersonEntity;
import io.github.paulushcgcj.repository.PersonRepository;
import jakarta.inject.Singleton;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
@RequiredArgsConstructor
@Slf4j
public class PersonService {

  private final PersonRepository personRepository;

  public Flux<PersonDto> listAll() {
    log.info("Listing all persons");
    return personRepository
        .findAll()
        .map(this::convertToDto)
        .doOnNext(personDto -> log.info("Person: {}", personDto));
  }

  public Mono<PersonDto> findById(Long id) {
    log.info("Finding person by id: {}", id);
    return personRepository
        .findById(id)
        .map(this::convertToDto);
  }

  public Mono<PersonDto> save(PersonDto personDto) {
    log.info("Saving person: {}", personDto);
    return
        personRepository
            .save(convertToEntity(personDto))
            .map(this::convertToDto);
  }


  private PersonDto convertToDto(PersonEntity personEntity) {
    return PersonDto
        .builder()
        .id(personEntity.getId())
        .firstName(personEntity.getFirstName())
        .lastName(personEntity.getLastName())
        .email(personEntity.getEmail())
        .gender(personEntity.getGender())
        .build();
  }

  private PersonEntity convertToEntity(PersonDto personDto) {
    return PersonEntity
        .builder()
        .firstName(personDto.firstName())
        .lastName(personDto.lastName())
        .email(personDto.email())
        .build();
  }
}
