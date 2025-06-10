package finalmission.member.controller;

import finalmission.login.authentication.AuthenticationPrincipal;
import finalmission.login.authorization.handler.AuthorizationHandler;
import finalmission.login.authorization.handler.TokenCookieHandler;
import finalmission.member.dto.MemberLoginRequest;
import finalmission.member.dto.MemberResponse;
import finalmission.member.dto.MemberTokenResponse;
import finalmission.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final MemberService memberService;
    private final AuthorizationHandler<String> authorizationHandler;

    public LoginController(MemberService memberService, TokenCookieHandler tokenCookieHandler) {
        this.memberService = memberService;
        this.authorizationHandler = tokenCookieHandler;
    }

    @PostMapping
    public ResponseEntity<Void> login(
            @RequestBody MemberLoginRequest memberLoginRequest,
            HttpServletResponse httpServletResponse
    ) {
        MemberTokenResponse memberTokenResponse = memberService.createToken(memberLoginRequest);
        authorizationHandler.createCookie(memberTokenResponse.accessToken(), httpServletResponse);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<MemberResponse> checkLogin(
            @AuthenticationPrincipal MemberResponse memberResponse
    ) {
        return ResponseEntity.ok(memberResponse);
    }
}
