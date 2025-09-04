package finalmission.infrastructure.jwt;

import finalmission.dto.AuthenticatedMember;
import finalmission.domain.TokenAuthRole;
import finalmission.domain.TokenProcessor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProcessor implements TokenProcessor {

    private final JwtProperties jwtProperties;

    @Override
    public String createToken(TokenAuthRole tokenAuthRole, Long id) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(jwtProperties.getSignKey())
                .claim("id", id.toString())
                .claim("role", tokenAuthRole.name())
                .compact();
    }

    @Override
    public AuthenticatedMember extract(String rawToken) {
        validateToken(rawToken);
        Claims body = (Claims) Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSignKey())
                .build()
                .parse(rawToken.split(" ")[1])
                .getBody();

        return new AuthenticatedMember(
                TokenAuthRole.findByName(body.get("role", String.class)),
                Long.valueOf(body.get("id", String.class))
        );
    }

    private void validateToken(String rawToken) {
        if (rawToken == null || !rawToken.startsWith("Bearer")) {
            throw new IllegalArgumentException("잘못된 토큰 형식입니다.");
        }
    }
}
