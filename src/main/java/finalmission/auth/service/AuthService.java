package finalmission.auth.service;

import finalmission.auth.dto.LoginMember;
import finalmission.auth.dto.LoginRequest;
import finalmission.member.domain.Member;
import finalmission.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String SECRET_KEY = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    private final MemberRepository memberRepository;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String login(LoginRequest request) {
        Member member = memberRepository.findByEmailAndPassword(request.email(), request.password())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));

        return Jwts.builder()
                .setSubject(member.getId().toString())
                .claim("name", member.getName())
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    public LoginMember findAuthenticatedMember(String token) {
        Long id = extractMemberIdFromToken(token);
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return LoginMember.from(member);
    }

    private Long extractMemberIdFromToken(String token) {
        return Long.valueOf(Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }
}
