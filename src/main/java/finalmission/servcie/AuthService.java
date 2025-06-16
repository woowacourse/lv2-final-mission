package finalmission.servcie;

import finalmission.domain.Member;
import finalmission.dto.layer.AccessTokenContent;
import finalmission.dto.layer.LoginContent;
import finalmission.exception.LoginException;
import finalmission.repository.MemberRepository;
import finalmission.utility.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public AuthService(MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    @Transactional(readOnly = true)
    public String login(LoginContent loginContent) {
        Member member = getMemberByEmail(loginContent.email());
        validatePassword(member, loginContent.password());
        return jwtProvider.makeAccessToken(new AccessTokenContent(member.getId(), member.getRole()));
    }

    private Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new LoginException("이메일에 해당하는 회원이 존재하지 않습니다."));
    }

    private void validatePassword(Member member, String password) {
        if (!member.comparePassword(password)) {
            throw new LoginException("비밀번호가 맞지 않습니다.");
        }
    }
}
