package finalmission.service;

import finalmission.domain.Member;
import finalmission.dto.request.LoginRequest;
import finalmission.exception.EmailNotExistException;
import finalmission.infrastructure.MemberRepository;
import finalmission.infrastructure.PasswordClient;
import finalmission.infrastructure.TokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordClient passwordClient;

    public AuthService(MemberRepository memberRepository, TokenProvider tokenProvider, PasswordClient passwordClient) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
        this.passwordClient = passwordClient;
    }

    public String login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(EmailNotExistException::new);
        member.validatePassword(request.password());
        return tokenProvider.issue(member);
    }

    public PasswordResponse recommendPassword() {
        return new PasswordResponse(passwordClient.getRecommendedPassword());
    }
}
