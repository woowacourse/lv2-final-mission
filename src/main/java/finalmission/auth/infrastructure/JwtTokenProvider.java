package finalmission.auth.infrastructure;

import finalmission.exception.auth.ExpiredTokenException;
import finalmission.exception.auth.InvalidTokenException;
import finalmission.member.domain.Member;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final Key secretKey;
    private final Long tokenValidTime;

    public JwtTokenProvider(
            @Value("${auth.jwt.secret-key}") String secretKey,
            @Value("${auth.jwt.valid-time}") Long tokenValidTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.tokenValidTime = tokenValidTime;
    }

    @Override
    public String issue(Member member) {
        return Jwts.builder()
                .claim("id", String.valueOf(member.getId()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String extractMemberId(String token) {
        validateToken(token);
        return createTokenParser()
                .parseClaimsJws(token)
                .getBody()
                .get("id", String.class);
    }

    private void validateToken(String token) {
        try {
            createTokenParser().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException();
        }
    }

    private JwtParser createTokenParser() {
        return Jwts.parserBuilder().setSigningKey(secretKey).build();
    }


}
