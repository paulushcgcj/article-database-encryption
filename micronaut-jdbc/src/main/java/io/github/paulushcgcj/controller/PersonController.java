package io.github.paulushcgcj.controller;

import io.github.paulushcgcj.dto.PersonDto;
import io.github.paulushcgcj.service.PersonService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller("/persons")
@Serdeable
@Slf4j
@RequiredArgsConstructor
public class PersonController {

  private final PersonService personService;

  @Get
  @Produces(MediaType.APPLICATION_JSON)
  public List<PersonDto> listAll() {
    log.info("Listing all persons");
    return personService.listAll();
  }


  @Get("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public PersonDto findById(Long id) {
    log.info("Finding person by id: {}", id);
    return personService.findById(id);
  }

  @Post
  @Produces(MediaType.APPLICATION_JSON)
  public PersonDto save(PersonDto personDto) {
    log.info("Saving person: {}", personDto);
    return personService.save(personDto);
  }

}
