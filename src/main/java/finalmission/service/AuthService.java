package finalmission.service;

import finalmission.dto.LoginRequestDto;
import finalmission.model.Member;
import finalmission.repository.MemberRepository;
import finalmission.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findByEmail(loginRequestDto.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원정보입니다."));

        if (!member.hasSamePassword(loginRequestDto.password())) {
            throw new IllegalArgumentException("올바르지 않는 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(String.valueOf(member.getId()));
    }
}
