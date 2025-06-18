package finalmission.member.service;

import finalmission.common.JwtTokenProvider;
import finalmission.member.domain.Member;
import finalmission.member.dto.LoginMember;
import finalmission.member.dto.LoginRequest;
import finalmission.member.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private JwtTokenProvider tokenProvider;
    private MemberRepository memberRepository;

    public LoginService(JwtTokenProvider tokenProvider, MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public String loginAndReturnToken(LoginRequest request) {
        Member member = memberRepository.findByEmailAndPassword(
                request.email(),
                request.password()
        );
        return tokenProvider.createToken(member);
    }

    @Transactional
    public LoginMember loginCheck(String token) {
        Long memberId = tokenProvider.getMemberId(token);
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 회원입니다."));
        return new LoginMember(memberId, findMember.getName());
    }
}
