package finalmission.business.service;

import finalmission.auth.AuthToken;
import finalmission.auth.jwt.JwtUtil;
import finalmission.business.model.entity.Member;
import finalmission.exception.AuthenticationException;
import finalmission.infrastructure.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    public AuthToken authenticate(final String email, final String password) {
        final Member member = memberRepository.findByEmail(email);
        if (!password.equals(member.getPassword())) {
            throw new AuthenticationException("Wrong password");
        }
        return jwtUtil.createToken(member);
    }

}
