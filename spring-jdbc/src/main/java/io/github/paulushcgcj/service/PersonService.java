package io.github.paulushcgcj.service;

import io.github.paulushcgcj.dto.PersonDto;
import io.github.paulushcgcj.entity.PersonEntity;
import io.github.paulushcgcj.repository.PersonRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService {

  private final PersonRepository personRepository;

  public List<PersonDto> listAll() {
    log.info("Listing all persons");
    return personRepository
        .findAll()
        .stream()
        .map(this::convertToDto)
        .peek(personDto -> log.info("Person: {}", personDto))
        .collect(Collectors.toList());
  }

  public PersonDto findById(Long id) {
    log.info("Finding person by id: {}", id);
    return personRepository
        .findById(id)
        .map(this::convertToDto)
        .orElse(null);
  }

  public PersonDto save(PersonDto personDto) {
    log.info("Saving person: {}", personDto);
    PersonEntity personEntity = convertToEntity(personDto);
    personEntity = personRepository.save(personEntity);
    return convertToDto(personEntity);
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
