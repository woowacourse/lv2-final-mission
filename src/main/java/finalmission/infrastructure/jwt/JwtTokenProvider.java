package finalmission.infrastructure.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    public JwtTokenProvider(final JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String createToken(String payload) {
        return Jwts.builder()
                .subject(payload)
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.validTime()))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String extractSubject(String token) {
        return createTokenParser()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private JwtParser createTokenParser() {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(jwtProperties.secretKey().getBytes(StandardCharsets.UTF_8)))
                .build();
    }
}
