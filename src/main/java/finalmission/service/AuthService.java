package finalmission.service;

import finalmission.controller.dto.MemberInfo;
import finalmission.domain.Member;
import finalmission.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final String SECRET_KEY = "/VCEkdrvbjkCLRp0G0LK12VsDYViO/kufd8UVphCq80MdiZzNlefqOcwqfpvW+jIRNBmhiNKbNUGXSwum0DSgUjkEl2To/350R/ffncBkVeO7tfNq/4rNXWGGHre9p8s14m0k2z/QS4=";

    private final MemberRepository memberRepository;

    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String login(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 혹은 비밀번호입니다."));
        return Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public MemberInfo getMemberInfoByToken(String token) {
        String memberId = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return new MemberInfo(Long.parseLong(memberId));
    }
}
