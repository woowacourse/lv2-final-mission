package finalmission.member.auth.util;

import finalmission.common.exception.AuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtExtractor {

    private static final String TOKEN_NAME = "token";

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public Long extractMemberId(String token) {
        return Long.valueOf(extractClaims(token).getSubject());
    }

    public String extractToken(Cookie[] cookies) {
        if (cookies == null) {
            throw new AuthorizationException("쿠키가 존재하지 않습니다.");
        }

        for (Cookie cookie : cookies) {
            if (TOKEN_NAME.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        throw new AuthorizationException("쿠키에 Token 정보가 없습니다.");
    }

    private Claims extractClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthorizationException("만료된 토큰입니다.");
        } catch (MalformedJwtException e) {
            throw new AuthorizationException("잘못된 토큰입니다.");
        } catch (JwtException e) {
            throw new AuthorizationException("유효하지 않은 토큰입니다.");
        }
    }
}
