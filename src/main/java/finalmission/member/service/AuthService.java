package finalmission.member.service;

import finalmission.global.auth.dto.LoginMember;
import finalmission.global.auth.util.JwtUtil;
import finalmission.global.error.exception.AuthenticationFailException;
import finalmission.member.dto.request.LoginRequest;
import finalmission.member.entity.Member;
import finalmission.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public AuthService(JwtUtil jwtUtil, MemberRepository memberRepository) {
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
    }

    public String login(LoginRequest request) {
        Member member = findMemberByEmailOrThrow(request.email());
        validatePasswordCorrect(member.getPassword(), request.password());

        LoginMember loginMember = new LoginMember(member.getId(), member.getName(), member.getRole());
        return jwtUtil.createToken(loginMember);
    }

    private Member findMemberByEmailOrThrow(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationFailException("이메일 또는 비밀번호가 일치하지 않습니다."));
    }

    private void validatePasswordCorrect(String memberPassword, String requestPassword) {
        if (!memberPassword.equals(requestPassword)) {
            throw new AuthenticationFailException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }
}
