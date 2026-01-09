package finalmission.auth;

import finalmission.member.Member;
import finalmission.member.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private final String secretKey;
    private final MemberRepository memberRepository;

    public JwtTokenService(@Value("${jwt.key}") String secretKey,
        MemberRepository memberRepository) {
        this.secretKey = secretKey;
        this.memberRepository = memberRepository;
    }

    public String getToken(Member member) {
        return Jwts.builder()
            .claim(Claims.SUBJECT, member.getId().toString())
            .claim("email", member.getEmail())
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .compact();
    }

    public Member getMember(String token) {
        Long id = Long.valueOf(Jwts.parser()
            .setSigningKey(secretKey.getBytes())
            .parseClaimsJws(token)
            .getBody()
            .getSubject());
        return memberRepository.findById(id)
            .orElseThrow(() -> new AuthException("유효하지 않은 토큰입니다."));
    }
}
