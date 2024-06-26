package io.github.paulushcgcj.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.hibernate.annotations.ColumnTransformer;

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
    private Long id;

    @Column(name = "first_name", length = 50)
    @ColumnTransformer(
        read = "pgp_sym_decrypt(first_name, current_setting('cryptic.key'))",
        write = "pgp_sym_encrypt(?, current_setting('cryptic.key'))"
    )
    private String firstName;

    @Column(name = "last_name", length = 50)
    @ColumnTransformer(
        read = "pgp_sym_decrypt(last_name, current_setting('cryptic.key'))",
        write = "pgp_sym_encrypt(?, current_setting('cryptic.key'))"
    )
    private String lastName;

    @Column(name = "email", length = 50)
    @ColumnTransformer(
        read = "pgp_sym_decrypt(email, current_setting('cryptic.key'))",
        write = "pgp_sym_encrypt(?, current_setting('cryptic.key'))"
    )
    private String email;

    @Column(name = "gender", length = 50)
    @ColumnTransformer(
        read = "pgp_sym_decrypt(gender, current_setting('cryptic.key'))",
        write = "pgp_sym_encrypt(?, current_setting('cryptic.key'))"
    )
    private String gender;

}