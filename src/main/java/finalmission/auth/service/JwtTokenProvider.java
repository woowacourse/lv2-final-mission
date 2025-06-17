package finalmission.auth.service;

import finalmission.global.UnauthorizedException;
import finalmission.member.domain.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret-key}") final String secretKey,
                            @Value("${jwt.validity-in-milliseconds}") final long validityInMilliseconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(final Long id, final Role role) {
        final Claims claims = Jwts.claims().setSubject(id.toString());
        claims.put("role", role);
        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey)
                .compact();
    }

    public long getId(final String token) {
        final Claims claims = getClaims(token);
        try {
            return Long.parseLong(claims.getSubject());
        } catch (ArithmeticException e) {
            throw new UnauthorizedException("올바르지 않은 토큰 정보입니다.");
        }
    }

    public Role getRole(final String token) {
        final Claims claims = getClaims(token);
        return Role.valueOf(claims.get("role", String.class));
    }

    private Claims getClaims(final String token) {
        validateToken(token);
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public void validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                throw new UnauthorizedException("만료된 토큰입니다.");
            }
        } catch (JwtException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new UnauthorizedException("인증할 수 없는 토큰입니다.");
        }
    }
}
