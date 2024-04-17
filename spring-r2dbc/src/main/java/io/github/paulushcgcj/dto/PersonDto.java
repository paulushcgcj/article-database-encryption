package io.github.paulushcgcj.dto;

import lombok.Builder;
import lombok.With;

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
