package finalmission.member.service;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRole;
import finalmission.member.dto.LoginRequest;
import finalmission.member.dto.LoginResponse;
import finalmission.member.dto.SignupRequest;
import finalmission.member.dto.SignupResponse;
import finalmission.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public LoginResponse login(final LoginRequest loginRequest) {
        Member member = memberRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 잘못되었습니다."));
        return new LoginResponse(member.getId());
    }

    public SignupResponse signUp(final SignupRequest signupRequest) {
        Member member = new Member(signupRequest.nickname(), signupRequest.email(), signupRequest.password(), MemberRole.REGULAR);
        Member savedMember = memberRepository.save(member);
        return new SignupResponse(savedMember.getId());
    }
}
