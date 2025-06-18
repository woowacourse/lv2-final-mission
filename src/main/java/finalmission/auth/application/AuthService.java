package finalmission.auth.application;

import finalmission.auth.application.dto.LoginRequest;
import finalmission.member.domain.Member;
import finalmission.member.domain.PasswordEncoder;
import finalmission.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequest request) {
        Member member = memberRepository.findByEmailValue(request.email())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        checkPasswordMatched(request, member);

        return tokenProvider.createToken(member);
    }

    private void checkPasswordMatched(LoginRequest request, Member member) {
        if (!passwordEncoder.matches(request.password(), member.getPassword().getValue())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
