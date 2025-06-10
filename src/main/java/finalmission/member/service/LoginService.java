package finalmission.member.service;

import finalmission.common.JwtTokenProvider;
import finalmission.member.domain.Member;
import finalmission.member.dto.LoginMember;
import finalmission.member.dto.LoginRequest;
import finalmission.member.infrastructure.MemberRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private JwtTokenProvider tokenProvider;
    private MemberRepository memberRepository;

    public LoginService(JwtTokenProvider tokenProvider, MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
    }

    public String loginAndReturnToken(LoginRequest request) {
        Member member = memberRepository.findByEmailAndPassword(
                request.email(),
                request.password()
        );
        return tokenProvider.createToken(member);
    }

    public LoginMember loginCheck(String token) {
        Long memberId = tokenProvider.getMemberId(token);
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 회원입니다.");
        }
        Member findMember = member.get();
        return new LoginMember(memberId, findMember.getName());
    }
}
