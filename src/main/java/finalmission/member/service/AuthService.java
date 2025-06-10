package finalmission.member.service;

import finalmission.common.exception.InvalidRequestException;
import finalmission.common.jwt.JwtTokenProvider;
import finalmission.member.domain.Member;
import finalmission.member.dto.LoginRequest;
import finalmission.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    public String publishLoginToken(LoginRequest loginRequestDto) {
        String email = loginRequestDto.email();
        String password = loginRequestDto.password();

        Member member = memberRepository.findByEmailAndPassword(email, password).orElseThrow(
                () -> new InvalidRequestException("이메일이나 비밀번호가 올바르지 않습니다."));
        return jwtTokenProvider.createJwtToken(member);
    }
}
