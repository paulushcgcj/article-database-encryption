package io.github.paulushcgcj.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "person", schema = "secure")
@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@Builder
public class PersonEntity {

  @Id
  private Long id;

  @Column(value = "first_name")
  private String firstName;

  @Column(value = "last_name")
  private String lastName;

  @Column(value = "email")
  private String email;

  @Column(value = "gender")
  private String gender;

}