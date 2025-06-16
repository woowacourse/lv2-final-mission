package finalmission.utility;

import finalmission.domain.Role;
import finalmission.dto.layer.AccessTokenContent;
import finalmission.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private SecretKey secretKey;
    private long expiredTime;

    public JwtProvider(
            @Value("${security.jwt.token.secret-key}") String secretKey,
            @Value("${security.jwt.token.expire-length}") long expiredTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.expiredTime = expiredTime;
    }

    public String makeAccessToken(AccessTokenContent content) {
        Date expiredDate = new Date(new Date().getTime() + expiredTime);
        return Jwts.builder()
                .claim("id", content.memberId())
                .claim("role", content.role())
                .issuedAt(new Date())
                .expiration(expiredDate)
                .signWith(secretKey)
                .compact();
    }

    public AccessTokenContent parseAccessToken(String accessToken) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
            Long memberId = Long.valueOf(claims.get("id").toString());
            Role role = Role.valueOf(claims.get("role").toString());
            return new AccessTokenContent(memberId, role);
        } catch (JwtException | IllegalArgumentException exception) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        }
    }
}
