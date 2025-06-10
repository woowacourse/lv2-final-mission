package finalmission.auth.application;

import finalmission.auth.domain.AuthTokenProvider;
import finalmission.auth.ui.dto.LoginRequest;
import finalmission.exception.auth.AuthenticationException;
import finalmission.exception.resource.ResourceNotFoundException;
import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenProvider authTokenProvider;
    private final MemberRepository memberRepository;

    public String createAccessToken(final LoginRequest request) {
        final Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("해당 이메일을 가진 회원이 존재하지 않습니다."));

        if (member.isWrongPassword(request.password())) {
            throw new AuthenticationException("비밀번호가 올바르지 않습니다.");
        }

        return authTokenProvider.createAccessToken(member.getId().toString(), member.getRole());
    }
}
