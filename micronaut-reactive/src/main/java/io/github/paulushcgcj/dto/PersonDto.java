package io.github.paulushcgcj.dto;

import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import lombok.With;

@Serdeable
@ReflectiveAccess
@Builder
@With
public record PersonDto(
    Long id,
    String firstName,
    String lastName,
    String email,
    String gender
) {

}

