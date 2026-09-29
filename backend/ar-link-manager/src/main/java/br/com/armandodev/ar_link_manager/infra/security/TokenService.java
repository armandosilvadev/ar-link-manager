package br.com.armandodev.ar_link_manager.infra.security;

import br.com.armandodev.ar_link_manager.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secretKey;

    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.create()
                    .withIssuer("ar-link-manager")
                    .withSubject(user.getEmail())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error while generating token", exception);
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm)
                .withIssuer("ar-link-manager")
                .build()
                .verify(token)
                .getSubject();
    }

    public Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
