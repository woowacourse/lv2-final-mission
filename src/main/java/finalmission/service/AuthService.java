package finalmission.service;

import finalmission.domain.Member;
import finalmission.dto.request.LoginRequest;
import finalmission.exception.LoginFailedException;
import finalmission.infrastructure.MemberRepository;
import finalmission.infrastructure.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {
    private final JwtTokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public AuthService(final JwtTokenProvider tokenProvider, final MemberRepository memberRepository) {
        this.tokenProvider = tokenProvider;
        this.memberRepository = memberRepository;
    }

    public String createToken(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(LoginFailedException::new);
        member.validatePassword(loginRequest.password());

        return tokenProvider.createToken(member.getName());
    }
}
