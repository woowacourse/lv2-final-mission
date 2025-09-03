package finalmission.service;

import finalmission.domain.entity.Member;
import finalmission.dto.LoginRequest;
import finalmission.repository.MemberRepository;
import finalmission.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public String login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!member.getPassword().equals(loginRequest.password())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return jwtTokenProvider.createToken(member.getId(), member.getName(), member.getRole());
    }
}
