package finalmission.common;

import finalmission.member.domain.Member;
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

    public JwtTokenProvider(@Value("${security.jwt.token.secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(Member member) {
        Date expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);

        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("name", member.getName())
                .claim("role", member.getRole())
                .setExpiration(expirationDate)
                .signWith(secretKey)
                .compact();
    }

    public Long getMemberId(String token) {
        try {
            String id = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return Long.parseLong(id);
        } catch (JwtException e) {
            throw new IllegalArgumentException("올바르지 않은 토큰 형태입니다.");
        }
    }
}
