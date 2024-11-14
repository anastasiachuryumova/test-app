package ru.test.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс для хранения ключей для работы с JWT токенами.
 */
@ConfigurationProperties("jwt")
@AllArgsConstructor
@Getter
public class JwtUtilProperties {
    private final String secret = "secret";
    private final String subject = "User details";
    private final String issuer = "STUDENT";
}
