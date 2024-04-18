package io.github.paulushcgcj.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;


@Entity
@Table(name = "person", schema = "secure")
@NoArgsConstructor
@AllArgsConstructor
@Data
@With
@Builder
public class PersonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "first_name", length = 50)
  @NotNull
  @NotBlank
  private String firstName;

  @Column(name = "last_name", length = 50)
  @NotNull
  @NotBlank
  private String lastName;

  @Column(name = "email", length = 50)
  @Email
  @NotNull
  @NotBlank
  private String email;

  @Column(name = "gender", length = 50)
  @NotNull
  @NotBlank
  private String gender;

}
