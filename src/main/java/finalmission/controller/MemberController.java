package finalmission.controller;

import finalmission.dto.request.LoginRequest;
import finalmission.dto.request.SignUpRequest;
import finalmission.dto.response.SignUpResponse;
import finalmission.service.MemberService;
import finalmission.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/login")
    public void login(@RequestBody final LoginRequest request, final HttpServletResponse response) {

        String token = memberService.login(request);
        CookieUtil.addCookie("token", token, response);
    }

    @GetMapping("/logout")
    public void logout(final HttpServletResponse response) {
        CookieUtil.expireCookie("token", response);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signup(@Valid @RequestBody final SignUpRequest request) {
        return memberService.signup(request);
    }
}
