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
  private byte[] firstName;

  @Column(value = "last_name")
  private byte[] lastName;

  @Column(value = "email")
  private byte[] email;

  @Column(value = "gender")
  private byte[] gender;


}