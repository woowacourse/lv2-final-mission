package finalmission.common.jwt;

import finalmission.common.exception.LoginException;
import finalmission.member.domain.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final Duration EXPIRATION_TIME = Duration.ofHours(1);
    private static final String SECRET_KEY = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwtToken(final Member member) {
        return Jwts.builder()
                .subject(member.getId().toString())
                .claim("name", member.getName())
                .claim("role", member.getRole().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME.toMillis()))
                .signWith(secretKey)
                .compact();
    }

    public Long getMemberId(final String token) {
        try {
            String id = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return Long.parseLong(id);
        } catch (JwtException e) {
            throw new LoginException("올바르지 않은 토큰 형태입니다.");
        }
    }

    public String extractTokenFromCookie(Cookie[] cookies) {
        if (cookies == null) {
            throw new LoginException("[ERROR] 쿠키가 존재하지 않습니다.");
        }
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "token")) {
                return cookie.getValue();
            }
        }
        throw new LoginException("[ERROR] 접근 권한이 없습니다.");
    }
}
