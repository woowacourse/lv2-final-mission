package finalmission.service;

import finalmission.domain.LoginInfo;
import finalmission.domain.Member;
import finalmission.exception.MemberException;
import finalmission.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String loginMember(LoginInfo loginInfo) {
        Member member = memberRepository.findByEmailAndPassword(loginInfo.email(), loginInfo.password())
                .orElseThrow(() -> {
                    throw new MemberException("없는 멤버입니다.");
                });
        String secret = "hihi";
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        String jwt = Jwts.builder()
                .setSubject(member.getName())
                .signWith(secretKey)
                .compact();
        return jwt;
    }
}
