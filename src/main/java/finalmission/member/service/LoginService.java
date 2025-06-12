package finalmission.member.service;

import finalmission.member.auth.JwtTokenProvider;
import finalmission.member.domain.Member;
import finalmission.member.dto.request.LoginRequest;
import finalmission.member.exception.LoginException;
import finalmission.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginRequest loginRequest) {
        Member loginMember = memberRepository.findByEmailAndPassword(loginRequest.email(), loginRequest.password())
            .orElseThrow(() -> new LoginException("로그인 정보가 일치하지 않습니다."));

        return jwtTokenProvider.createToken(loginMember.getId(), loginMember.getRole());
    }
}
