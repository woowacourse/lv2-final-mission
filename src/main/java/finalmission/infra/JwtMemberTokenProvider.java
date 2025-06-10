package finalmission.infra;

import finalmission.domain.AuthenticationException;
import finalmission.domain.Member;
import finalmission.domain.MemberTokenProvider;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtMemberTokenProvider implements MemberTokenProvider {

    private final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public String generateToken(final Member member) {
        return Jwts.builder()
            .subject(member.getId())
            .signWith(SECRET_KEY)
            .compact();
    }

    public String extractId(final String token) {
        try {
            return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        } catch (JwtException e) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }
    }
}
