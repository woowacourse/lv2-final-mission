package finalmission.ballparkreservation.auth;

import finalmission.ballparkreservation.member.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private static final String SECRET_KEY = "foiehfiowhefoihadskfjnsdkjnkjdcbHpqowedioj";

    public String createToken(final Member member) {
        return Jwts.builder()
                .setSubject(member.getId().toString())
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }
}
