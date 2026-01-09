package finalmission.auth;

import finalmission.member.Member;
import finalmission.member.MemberRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;

    public String login(LoginRequest request) {
        Member member = memberRepository.findByEmailAndPassword(request.email(), request.password())
            .orElseThrow(NoSuchElementException::new);
        return jwtTokenService.getToken(member);
    }

    public LoginMember getMemberByToken(String token) {
        Member member = jwtTokenService.getMember(token);
        return new LoginMember(member.getEmail());
    }
}
