package finalmission.application;

import finalmission.application.dto.request.LoginRequest;
import finalmission.application.dto.response.LoginResponse;
import finalmission.application.support.exception.AuthException;
import finalmission.application.support.exception.NotFoundEntityException;
import finalmission.domain.Email;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.infrastructure.JwtPayload;
import finalmission.infrastructure.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(new Email(loginRequest.email()))
                .orElseThrow(() -> new NotFoundEntityException("해당 이메일을 가진 사용자가 존재하지 않습니다."));

        if (!member.isPassword(loginRequest.password())) {
            throw new AuthException("로그인에 실패하였습니다.");
        }

        String token = jwtProvider.issue(new JwtPayload(member.getId(), member.getName(), member.getRole()));
        return new LoginResponse(token);
    }
}
