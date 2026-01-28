package finalmission.auth;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import finalmission.user.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

    private final Key key;
    private final long expTime;

    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}") long expTime
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expTime = expTime;
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims();
        claims.put("userId", user.getId());

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getUserId(String token) {
        try {
            Object userId = parseClaims(token).get("userId", Object.class);
            if (userId instanceof Double d) {
                return d.longValue();
            }
            return (Long)userId;
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty.", e);
        }
    }

    public Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
    }
}
