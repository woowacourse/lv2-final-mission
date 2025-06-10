package finalmission.service;

import finalmission.domain.Member;
import finalmission.dto.request.LoginRequest;
import finalmission.exception.EmailNotExistException;
import finalmission.infrastructure.MemberRepository;
import finalmission.infrastructure.TokenProvider;
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
                .orElseThrow(EmailNotExistException::new);
        member.validatePassword(request.password());
        return tokenProvider.issue(member);
    }
}
