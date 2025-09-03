package finalmission.util;

import finalmission.domain.Member;
import finalmission.domain.Role;
import finalmission.exception.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import java.util.Date;
import java.util.Objects;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey;
    private final long expirationTime;

    public JwtTokenProvider(
            @Value("${jwt.secret}") final String secret,
            @Value("${jwt.token.expiration-time}") final long expirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationTime = expirationTime;
    }

    public String createToken(final Member member) {
        return Jwts.builder()
                .subject(member.getId().toString())
                .claim("name", member.getName())
                .claim("role", member.getRole().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public Long extractId(final String token) {
        try {
            final String stringId = parseClaims(token).getSubject();
            return Long.parseLong(stringId);
        } catch (final NumberFormatException e) {
            throw new AuthenticationException("잘못된 사용자 ID 형식입니다.");
        }
    }

    public Role extractRole(final String token) {
        final String stringRole = parseClaims(token).get("role", String.class);
        return Role.valueOf(stringRole);
    }

    private Claims parseClaims(final String token) {
        try {
            final Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            final Claims claims = jws.getPayload();
            if (claims.getExpiration().before(new Date())) {
                throw new AuthenticationException("토큰이 만료되었습니다.");
            }
            return claims;
        } catch (final JwtException | IllegalArgumentException e) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }
    }

    public String extractTokenFromCookie(final Cookie[] cookies) {
        if (cookies == null) {
            throw new AuthenticationException("쿠키가 존재하지 않습니다.");
        }
        for (final Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "token")) {
                return cookie.getValue();
            }
        }
        throw new AuthenticationException("접근 권한이 없습니다.");
    }
}
