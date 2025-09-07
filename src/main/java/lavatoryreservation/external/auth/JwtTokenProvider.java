package lavatoryreservation.external.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.RequiredTypeException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import java.util.Date;
import lavatoryreservation.exception.AuthenticationException;
import lavatoryreservation.member.domain.Member;

public class JwtTokenProvider {

    private static final String COOKIE_KEY = "token";
    private static final String USERNAME_KEY = "username";
    private static final String NAME_KEY = "name";
    private static final int ONE_HOUR = 3600000;

    private final String secretKey;

    public JwtTokenProvider(String secretKey) {
        this.secretKey = secretKey;
    }

    public String createToken(Member member) {
        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim(USERNAME_KEY, member.getName())
                .claim(NAME_KEY, member.getName())
                .setExpiration(new Date(System.currentTimeMillis() + ONE_HOUR))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    public MemberAuthentication resolveToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Long id = Long.parseLong(claims.getSubject());
            String username = (String) claims.get(USERNAME_KEY);
            String name = (String) claims.get(NAME_KEY);
            return new MemberAuthentication(id, username, name);
        } catch (RequiredTypeException | IllegalArgumentException e) {
            throw new AuthenticationException("토큰 파싱에 실패하였습니다");
        } catch (Exception e) {
            throw new AuthenticationException("토큰 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public static String getCookieKey() {
        return COOKIE_KEY;
    }

    public Cookie createCookie(Member member) {
        return new Cookie(getCookieKey(), createToken(member));
    }
}
