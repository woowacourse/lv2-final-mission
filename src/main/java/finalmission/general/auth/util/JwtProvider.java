package finalmission.general.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    private static final String SECRET_KEY = "JWT_SECRET_KEY_USING_DENTAL_RESERVATION";

    public String generateToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(validity)
                .signWith(generateSigningKey())
                .compact();
    }

    private SecretKey generateSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String getUsername(String token) {
        Claims claims = (Claims) Jwts.parserBuilder()
                .setSigningKey(generateSigningKey())
                .build()
                .parse(token)
                .getBody();
        return claims.getSubject();
    }
}
