package finalmission.auth.jwt;

import finalmission.auth.AuthToken;
import finalmission.business.model.entity.Member;
import io.jsonwebtoken.ClaimJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JJWTJwtUtil implements JwtUtil {

    private final SecretKey secretKey;
    private final long expirationTime;

    public JJWTJwtUtil(@Value("${spring.jwt.secret}") String secret,
                       @Value("${spring.jwt.expirationMinute}") long expirationTime) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationTime * 60 * 1000;
    }

    @Override
    public AuthToken createToken(Member member) {
        String tokenValue = Jwts.builder()
                .subject(member.getId().toString())
                .expiration(calculateExp())
                .signWith(secretKey)
                .compact();
        return new AuthToken(tokenValue);
    }

    private Date calculateExp() {
        return new Date(new Date().getTime() + expirationTime);
    }

    @Override
    public Long validateAndResolveToken(AuthToken authToken) {
        try {
            final Claims claims = Jwts.parser().verifyWith(secretKey).build()
                    .parseSignedClaims(authToken.value()).getPayload();
            return Long.parseLong(claims.getSubject());
        } catch (ClaimJwtException e1) {
            throw new RuntimeException("expired token", e1);
        } catch (Exception e2) {
            throw new RuntimeException("invalid token", e2);
        }
    }
}
