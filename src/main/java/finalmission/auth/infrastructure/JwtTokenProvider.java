package finalmission.auth.infrastructure;

import finalmission.member.domain.Member;
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
}
