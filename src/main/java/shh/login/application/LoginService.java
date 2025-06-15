package shh.login.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shh.login.application.dto.LoginCheckRequest;
import shh.login.application.dto.LoginCheckResponse;
import shh.login.application.dto.LoginRequest;
import shh.login.application.dto.SignupRequest;
import shh.login.application.dto.SignupSuccessResponse;
import shh.login.application.dto.Token;
import shh.member.application.MemberService;
import shh.member.domain.Email;
import shh.member.domain.Member;
import shh.member.domain.MemberName;
import shh.member.domain.Password;
import shh.member.domain.Role;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberService memberService;
    private final JwtService jwtService;

    public Token login(final LoginRequest request) {
        final Member member = memberService.findByEmailAndPassword(request.email(), request.password());
        return jwtService.createToken(member);
    }

    public LoginCheckResponse checkLogin(final LoginCheckRequest request) {
        final Member member = memberService.findById(request.id());
        return LoginCheckResponse.from(member);
    }

    public SignupSuccessResponse signup(final SignupRequest request) {
        final Member member = new Member(
                null,
                new MemberName(request.name()),
                new Email(request.email()),
                new Password(request.password()),
                Role.MEMBER
        );
        final Member savedMember = memberService.saveMember(member);
        return SignupSuccessResponse.from(savedMember);
    }
}
