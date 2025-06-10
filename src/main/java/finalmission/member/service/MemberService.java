package finalmission.member.service;

import finalmission.auth.JwtTokenProvider;
import finalmission.member.domain.Member;
import finalmission.member.dto.SignupRequest;
import finalmission.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider tokenProvider;

    public MemberService(MemberRepository memberRepository, JwtTokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    public Cookie addMember(SignupRequest request) {
        Member newMember = memberRepository.save(new Member(null, request.name(), request.email(), request.password()));
        String token = tokenProvider.createToken(newMember.getId().toString());
        return new Cookie("token", token);
    }

    public Member findMemberByToken(String token) {
        String payload = tokenProvider.getPayload(token);
        return memberRepository.findById(Long.parseLong(payload)).orElseThrow();
    }
}
