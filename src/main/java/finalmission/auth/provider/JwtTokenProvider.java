package finalmission.auth.provider;

import finalmission.exception.AuthException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final Long tokenValidTime;

    public JwtTokenProvider(
            @Value("${auth.jwt.secret-key}") String secretKey,
            @Value("${auth.jwt.valid-time}") Long tokenValidTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.tokenValidTime = tokenValidTime;
    }

    public String createToken(String payload) {
        return Jwts.builder()
                .subject(payload)
                .expiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(secretKey)
                .compact();
    }

    public String extractSubject(String token) {
        validateToken(token);
        return createTokenParser()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private void validateToken(String token) {
        try {
            createTokenParser().parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new AuthException("토근이 만료되었습니다");
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthException("토큰 정보가 올바르지 않습니다");
        }
    }

    private JwtParser createTokenParser() {
        return Jwts.parser().verifyWith(secretKey).build();
    }


}
