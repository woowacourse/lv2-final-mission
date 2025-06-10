package finalmission.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    public String createToken(final Long id) {
        Date expirationDate = new Date(System.currentTimeMillis() + 3600000);

        return Jwts.builder()
                .setSubject(id.toString())
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public Long extractId(String token) {
        try {
            Claims payload = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return Long.valueOf(payload.getSubject());
        } catch (JwtException e) {
            throw new IllegalStateException("토큰 오류");
        }
    }

}
