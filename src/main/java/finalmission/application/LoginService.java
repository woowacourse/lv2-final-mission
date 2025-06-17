package finalmission.application;

import finalmission.domain.MemberCredential;
import finalmission.domain.repository.MemberCredentialRepository;
import finalmission.dto.RequestLogin;
import finalmission.infrastructure.security.AccessToken;
import finalmission.infrastructure.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberCredentialRepository memberCredentialRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public AccessToken login(RequestLogin requestLogin) {
        MemberCredential memberCredential = memberCredentialRepository.findByEmail(requestLogin.email())
                .orElseThrow(() -> new IllegalArgumentException("이메일과 비밀번호를 확인해 주세요."));
        boolean matches = memberCredential.matches(requestLogin.password());
        if (!matches) {
            throw new IllegalArgumentException("이메일과 비밀번호를 확인해 주세요.");
        }
        return jwtProvider.issue(memberCredential.getMemberId());
    }
}
