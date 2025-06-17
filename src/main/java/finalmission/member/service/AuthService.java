package finalmission.member.service;

import finalmission.global.auth.dto.LoginMember;
import finalmission.global.auth.util.JwtUtil;
import finalmission.global.error.exception.BadRequestException;
import finalmission.member.dto.request.LoginRequest;
import finalmission.member.dto.response.LoginResponse;
import finalmission.member.entity.Member;
import finalmission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("존재하지 않는 멤버입니다."));

        validatePassword(member, request.password());

        LoginMember loginMember = new LoginMember(member.getId(), member.getName(), member.getRole());
        String token = jwtUtil.createToken(loginMember);

        return LoginResponse.from(token);
    }

    private void validatePassword(Member member, String password) {
        if (!member.matchesPassword(password)) {
            throw new BadRequestException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }
}
