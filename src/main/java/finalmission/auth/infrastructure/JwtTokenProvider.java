package finalmission.auth.infrastructure;

import finalmission.auth.domain.AuthRole;
import finalmission.auth.domain.AuthTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements AuthTokenProvider {

    private final SecretKey secretKey;
    @Value("${security.jwt.access-token.validity-in-milliseconds}")
    private long validityInMilliseconds;

    public JwtTokenProvider(@Value("${security.jwt.access-token.secret-key}") final String secretKeyValue) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyValue.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(final String principal, final AuthRole role) {
        Claims claims = Jwts.claims()
                .subject(principal)
                .build();
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(validity)
                .claim("role", role.name())
                .signWith(secretKey)
                .compact();
    }

    public String getPrincipal(final String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public AuthRole getRole(final String token) {
        if (token == null || token.isEmpty()) {
            return AuthRole.GUEST;
        }

        String role = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);

        return AuthRole.valueOf(role);
    }

    public boolean isValidToken(final String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
