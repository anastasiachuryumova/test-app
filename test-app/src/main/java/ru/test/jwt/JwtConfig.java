package ru.test.jwt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.UUID;

/**
 * Конфигурация для JWT токенов
 */
public class JwtConfig {
    private final JwtUtilProperties jwtUtilProperties;

    public JwtConfig(JwtUtilProperties jwtUtilProperties) {
        this.jwtUtilProperties = jwtUtilProperties;
    }

    /**
     * Валидирует токен, вытаскивает из токена userId
     * @exception JWTVerificationException
     */
    public UUID getUserId(String token) {
        return validateAndExtractUserId(token);
    }

    private UUID validateAndExtractUserId(String token) {
        var decodedToken = JWT.require(Algorithm.HMAC256(jwtUtilProperties.getSecret()))
                .withSubject(jwtUtilProperties.getSubject())
                .withIssuer(jwtUtilProperties.getIssuer())
                .build()
                .verify(token);

        String userIdClaim = decodedToken.getClaim("userId").asString();
        if (userIdClaim == null) {
            throw new JWTVerificationException("Claim [userId] doesn't exist!");
        }

        return UUID.fromString(userIdClaim);
    }
}
