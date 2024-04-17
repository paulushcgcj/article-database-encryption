package io.github.paulushcgcj.util;

import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DatabaseCryptoUtil {

  private final R2dbcEntityTemplate template;

  public Mono<byte[]> decrypt(byte[] data) {
    return decryptAsString(data)
        .map(s -> s.getBytes(StandardCharsets.UTF_8));
  }

  public Mono<String> decryptAsString(byte[] data) {
    return template
        .getDatabaseClient()
        .sql("SELECT pgp_sym_decrypt(:data, current_setting('cryptic.key')) AS decrypted_data")
        .bind("data", data)
        .map((row, rowMetadata) -> row.get("decrypted_data", String.class))
        .first();
  }

  public Mono<byte[]> encrypt(byte[] data) {
    return encryptFromString(new String(data, StandardCharsets.UTF_8));
  }

  public Mono<byte[]> encryptFromString(String data) {
    return template
        .getDatabaseClient()
        .sql("SELECT pgp_sym_encrypt(:data, current_setting('cryptic.key')) AS encrypted_data")
        .bind("data", data)
        .map((row, rowMetadata) -> row.get("encrypted_data", byte[].class))
        .first();
  }

}
