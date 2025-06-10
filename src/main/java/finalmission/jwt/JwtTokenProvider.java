package finalmission.jwt;

import finalmission.entity.Member;
import finalmission.exception.custom.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "Yn2kjibddFAWUIGosrisjfaIUHatnPJ2AFlL8WXmohhdsf3a6=JMCvigQgfsdragaEtjgdfssypa5E";
    private final String ISSUER = "flint";
    private final int EXPIRE_DURATION = 36000000;

    public String createTokenByMember(Member member) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_DURATION);

        return Jwts.builder()
                .setSubject(member.getId().toString())
                .setIssuer(ISSUER)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .claim("name", member.getName())
                .claim("role", member.getRole())
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public Claims extractToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
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
