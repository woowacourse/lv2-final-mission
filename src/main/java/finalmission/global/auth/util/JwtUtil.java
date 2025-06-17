package finalmission.global.auth.util;

import finalmission.global.auth.dto.LoginMember;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.valid-seconds}")
    private long validSeconds;

    public String createToken(LoginMember loginMember) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + validSeconds);
        SecretKey encodedKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claim("id", loginMember.id())
                .claim("name", loginMember.name())
                .claim("role", loginMember.role())
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(encodedKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            return !Jwts.parserBuilder()
                    .setSigningKey(getEncodedKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getEncodedKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private SecretKey getEncodedKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
