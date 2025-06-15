package finalmission.auth.service;

import finalmission.auth.dto.request.LoginRequest;
import finalmission.auth.infrastructure.TokenProvider;
import finalmission.exception.auth.LoginFailedException;
import finalmission.member.domain.Member;
import finalmission.member.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public AuthService(MemberRepository memberRepository, TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
    }

    public String login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(LoginFailedException::new);
        member.validatePassword(request.password());

        return tokenProvider.issue(member);
    }
}
