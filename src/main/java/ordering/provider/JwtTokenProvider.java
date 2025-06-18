package ordering.provider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import ordering.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public String createToken(User user) {
        return Jwts.builder()
            .setSubject(user.getId().toString())
            .claim("name", user.getName())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .compact();
    }

    public Long getUserIdFromToken(String token) {
        try {
            return Long.valueOf(Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
        } catch (ExpiredJwtException e) {
            throw new IllegalStateException("토큰 만료");
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalStateException("유효하지 않은 토큰");
        }
    }
}
