package io.github.paulushcgcj.controller;

import io.github.paulushcgcj.dto.PersonDto;
import io.github.paulushcgcj.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

  private final PersonService personService;


  @GetMapping
  public Flux<PersonDto> listAll() {
    log.info("Listing all persons");
    return personService.listAll();
  }

  @GetMapping("/{id}")
  public Mono<PersonDto> findById(Long id) {
    log.info("Finding person by id: {}", id);
    return personService.findById(id);
  }

  @PostMapping
  public Mono<PersonDto> save(PersonDto personDto) {
    log.info("Saving person: {}", personDto);
    return personService.save(personDto);
  }

}
