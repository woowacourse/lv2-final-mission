package finalmission.controller;

import finalmission.domain.login.LoginMember;
import finalmission.domain.member.Member;
import finalmission.dto.LoginRequestDto;
import finalmission.domain.login.Token;
import finalmission.service.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login-coach")
    public Token coachLogin(@RequestBody LoginRequestDto loginRequestDto) {
        return loginService.coachLogin(loginRequestDto);
    }

    @PostMapping("/login-crew")
    public Token crewLogin(@RequestBody LoginRequestDto loginRequestDto) {
        return loginService.crewLogin(loginRequestDto);
    }

}
