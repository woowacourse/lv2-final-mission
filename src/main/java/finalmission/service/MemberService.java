package finalmission.service;

import finalmission.domain.Member;
import finalmission.dto.LoginInfo;
import finalmission.dto.SignUpInfo;
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
        String secret = "since-jjwt-api-0.11-secret-key-must-be-longer-than-32-bytes";
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        String jwt = Jwts.builder()
                .setSubject(member.getId().toString())
                .signWith(secretKey)
                .compact();
        return jwt;
    }

    public Member createMember(SignUpInfo signUpInfo) {
        Member member = new Member(null, signUpInfo.name(), signUpInfo.email(), signUpInfo.password());
        Member savedMember = memberRepository.save(member);

        return savedMember;
    }

    public Member findMemberById(Long id) {

        return memberRepository.findById(id).orElseThrow(() -> {
            throw new MemberException("없는 멤버입니다.");
        });
    }
}
