package finalmission.domain.login;

import finalmission.domain.member.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.access.expire-length}")
    private long accessTokenExpireTime;

    public Token createToken(Member member) {
        Date date = new Date();
        Date accessTokenExpiredAt = new Date(date.getTime() + accessTokenExpireTime);
        String accessToken = Jwts.builder()
            .claim("id", member.getId())
            .claim("memberType", member.getType())
            .issuedAt(date)
            .expiration(accessTokenExpiredAt)
            .signWith(convertFrom(secretKey), SIG.HS256)
            .compact();
        return new Token(accessToken);
    }

    public Claims getClaimsAndValidateToken(String token) {
        try {
        return Jwts.parser()
            .verifyWith(convertFrom(secretKey))
            .build()
            .parseSignedClaims(token)
            .getPayload();
        } catch (ExpiredJwtException e) {
            throw new IllegalStateException("유효기한이 만료된 토큰입니다.");
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
            throw new IllegalStateException("올바르지 않은 토큰입니다.");
        }
    }

    private SecretKey convertFrom(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
