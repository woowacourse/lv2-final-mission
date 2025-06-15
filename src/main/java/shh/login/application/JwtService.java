package shh.login.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shh.common.exception.UnauthorizedException;
import shh.login.application.dto.Token;
import shh.member.domain.Member;

@Service
public class JwtService {

    public static final String CLAIM_ID_KEY = "id";
    public static final String CLAIM_ROLE_KEY = "role";

    @Value("${security.jwt.token.secret-key}")
    private String secretKeyRaw;

    @Value("${security.jwt.token.access.expire-length}")
    private Long accessTokenExpireTime;

    private Key secretKey;

    @PostConstruct
    public void init() {
        final byte[] keyBytes = Base64.getDecoder().decode(secretKeyRaw);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public Token createToken(final Member member) {
        final Date date = new Date();
        final Date accessTokenExpiredAt = new Date(date.getTime() + accessTokenExpireTime);

        final String token = Jwts.builder()
                .claim(CLAIM_ID_KEY, member.getId())
                .claim(CLAIM_ROLE_KEY, member.getRole())
                .setIssuedAt(date)
                .setExpiration(accessTokenExpiredAt)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return new Token(token);
    }

    public Map<String, String> decode(final String token) {
        final Claims claims = parseToken(token);
        return Map.of(
                CLAIM_ID_KEY, claims.get(CLAIM_ID_KEY).toString().replaceAll("\\.0$", ""),
                CLAIM_ROLE_KEY, claims.get(CLAIM_ROLE_KEY).toString()
        );
    }

    private Claims parseToken(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (final ExpiredJwtException e) {
            throw new UnauthorizedException("로그인 정보가 만료되었습니다.");
        } catch (final UnsupportedJwtException | MalformedJwtException e) {
            throw new UnauthorizedException("로그인 정보가 유효하지 않습니다.");
        }
    }
}
