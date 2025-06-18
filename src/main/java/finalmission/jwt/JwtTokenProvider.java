package finalmission.jwt;

import finalmission.dto.request.CreateTokenRequest;
import finalmission.entity.Member;
import finalmission.exception.custom.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final String secretKey;
    private final String issuer;
    private final Integer expireDuration;

    public JwtTokenProvider(@Value("${jwt.secret-key}") final String secretKey,
                            @Value("${jwt.issuer}") final String issuer,
                            @Value("${jwt.expire-duration}") final Integer expireDuration) {
        this.secretKey = secretKey;
        this.issuer = issuer;
        this.expireDuration = expireDuration;
    }

    public String createTokenByMember(CreateTokenRequest request) {
        Date now = request.created();
        Date expireDate = new Date(now.getTime() + expireDuration);
        Member member = request.member();

        return Jwts.builder()
                .setSubject(member.getId().toString())
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim("name", member.getName())
                .claim("role", member.getRole())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public Claims extractToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .requireIssuer(issuer)
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("토큰이 만료되었습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        }
    }
}
