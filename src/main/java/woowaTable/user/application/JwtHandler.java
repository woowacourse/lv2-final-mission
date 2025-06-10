package woowaTable.user.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import woowaTable.common.exception.error.UnauthorizedException;
import woowaTable.user.application.dto.Token;
import woowaTable.user.domain.User;

@Component
public class JwtHandler {

    public static final String CLAIM_ID_KEY = "id";
    public static final String CLAIM_ROLE_KEY = "role";

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.access.expire-length}")
    private long accessTokenExpireTime;

    public Token createToken(final User user) {
        final Date date = new Date();
        final Date accessTokenExpiredAt = new Date(date.getTime() + accessTokenExpireTime);

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        final String accessToken = Jwts.builder()
                .claim(CLAIM_ID_KEY, user.getId())
                .claim(CLAIM_ROLE_KEY, user.getRole().toString())
                .setIssuedAt(date)
                .setExpiration(accessTokenExpiredAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return new Token(accessToken);
    }

    public Map<String, String> decode(final String token) {
        final Claims claims = parseJwt(token);

        return Map.of(
                CLAIM_ID_KEY, claims.get(CLAIM_ID_KEY).toString(),
                CLAIM_ROLE_KEY, claims.get(CLAIM_ROLE_KEY).toString()
        );
    }

    public String decode(final String token, final String key) {
        return parseJwt(token)
                .get(key)
                .toString();
    }

    private Claims parseJwt(final String token) {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey); // base64 인코딩된 키여야 함
            Key key = Keys.hmacShaKeyFor(keyBytes);

            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (final ExpiredJwtException e) {
            throw new UnauthorizedException("로그인 정보가 만료되었습니다.");
        } catch (final UnsupportedJwtException | MalformedJwtException | SignatureException e) {
            throw new UnauthorizedException("로그인 정보가 유효하지 않습니다.");
        }
    }
}
