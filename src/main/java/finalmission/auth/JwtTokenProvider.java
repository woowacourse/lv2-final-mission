package finalmission.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private static final int VALIDITY_IN_MILLISECONDS = 3600000;

    public String createToken(final Long id) {
        Date expirationDate = new Date(System.currentTimeMillis() + VALIDITY_IN_MILLISECONDS);

        return Jwts.builder()
                .subject(id.toString())
                .expiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public Long extractId(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }

        Claims claims = extractAllClaimsFromToken(token);
        return Long.valueOf(claims.getSubject());
    }

    private Claims extractAllClaimsFromToken(final String token) {
        try {
            return Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new IllegalArgumentException("토큰을 추출하는데 오류가 발생했습니다.");
        }
    }
}
