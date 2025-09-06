package finalmission.member.jwt;

import finalmission.exception.UnauthorizedException;
import finalmission.member.model.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final int jwtExpirationMs;
    private final SecretKey key;

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String jwtSecret,
                            @Value("${security.jwt.token.expire-length}") int jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(TokenCreateRequest request) {
        return Jwts.builder()
                .subject(request.memberId().toString())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public Long getMemberIdFromToken(String token) {
        return Long.valueOf(getJwtClaims(token).getSubject());
    }

    private Claims getJwtClaims(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException e) {
            throw UnauthorizedException.jwtTokenInvalid();
        } catch (ExpiredJwtException e) {
            throw UnauthorizedException.jwtTokenExpired();
        } catch (IllegalArgumentException e) {
            throw UnauthorizedException.jwtTokenEmpty();
        }
    }
}
