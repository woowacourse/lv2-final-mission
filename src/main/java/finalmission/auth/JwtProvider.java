package finalmission.auth;

import java.security.Key;
import java.util.Date;
import finalmission.exception.AuthNotValidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    private final Key key;
    private final Long validityMilliseconds;

    public JwtProvider(
            @Value("${secret.jwt-key}") String secretKey,
            @Value("${secret.jwt-expiration}") Long validityMilliseconds) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.validityMilliseconds = validityMilliseconds;
    }

    public String provideToken(final String phoneNumber) {
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityMilliseconds);

        return Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isValidToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (final JwtException e) {
            return false;
        }
    }

    public String extractSubject(final String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (final JwtException e) {
            throw new AuthNotValidTokenException();
        }
    }
}
