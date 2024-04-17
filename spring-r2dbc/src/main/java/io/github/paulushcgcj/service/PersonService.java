package io.github.paulushcgcj.service;

import io.github.paulushcgcj.dto.PersonDto;
import io.github.paulushcgcj.entity.PersonEntity;
import io.github.paulushcgcj.repository.PersonRepository;
import io.github.paulushcgcj.util.DatabaseCryptoUtil;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;
  private final DatabaseCryptoUtil databaseCryptoUtil;

  public Flux<PersonDto> listAll() {
    log.info("Listing all persons");
    return personRepository
        .findAll()
        .flatMap(this::decrypt)
        .map(this::convertToDto)
        .doOnNext(personDto -> log.info("Person: {}", personDto));
  }

  public Mono<PersonDto> findById(Long id) {
    log.info("Finding person by id: {}", id);
    return personRepository
        .findById(id)
        .flatMap(this::decrypt)
        .map(this::convertToDto);
  }

  public Mono<PersonDto> save(PersonDto personDto) {
    log.info("Saving person: {}", personDto);
    return
        encrypt(convertToEntity(personDto))
            .flatMap(personRepository::save)
            .map(this::convertToDto);
  }

  private PersonDto convertToDto(PersonEntity personEntity) {
    return PersonDto
        .builder()
        .id(personEntity.getId())
        .firstName(new String(personEntity.getFirstName(), StandardCharsets.UTF_8))
        .lastName(new String(personEntity.getLastName(), StandardCharsets.UTF_8))
        .email(new String(personEntity.getEmail(), StandardCharsets.UTF_8))
        .gender(new String(personEntity.getGender(), StandardCharsets.UTF_8))
        .build();
  }

  private PersonEntity convertToEntity(PersonDto personDto) {
    return PersonEntity
        .builder()
        .firstName(personDto.firstName().getBytes(StandardCharsets.UTF_8))
        .lastName(personDto.lastName().getBytes(StandardCharsets.UTF_8))
        .email(personDto.email().getBytes(StandardCharsets.UTF_8))
        .gender(personDto.gender().getBytes(StandardCharsets.UTF_8))
        .build();
  }

  private Mono<PersonEntity> decrypt(PersonEntity personEntity) {
    return
        databaseCryptoUtil
            .decrypt(personEntity.getFirstName())
            .map(personEntity::withFirstName)
            .flatMap(entity -> databaseCryptoUtil
                .decrypt(entity.getLastName())
                .map(entity::withLastName)
            )
            .flatMap(entity -> databaseCryptoUtil
                .decrypt(entity.getEmail())
                .map(entity::withEmail)
            )
            .flatMap(entity -> databaseCryptoUtil
                .decrypt(entity.getGender())
                .map(entity::withGender)
            );
  }

  private Mono<PersonEntity> encrypt(PersonEntity personEntity) {
    return
        databaseCryptoUtil
            .encrypt(personEntity.getFirstName())
            .map(personEntity::withFirstName)
            .flatMap(entity -> databaseCryptoUtil
                .encrypt(entity.getLastName())
                .map(entity::withLastName)
            )
            .flatMap(entity -> databaseCryptoUtil
                .encrypt(entity.getEmail())
                .map(entity::withEmail)
            )
            .flatMap(entity -> databaseCryptoUtil
                .encrypt(entity.getGender())
                .map(entity::withGender)
            );
  }
}

