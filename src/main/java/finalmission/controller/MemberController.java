package finalmission.controller;

import finalmission.controller.config.CookieManager;
import finalmission.controller.dto.MemberLoginRequest;
import finalmission.controller.dto.MemberResponse;
import finalmission.controller.dto.MemberSignupRequest;
import finalmission.domain.Member;
import finalmission.global.config.AuthenticationPrincipal;
import finalmission.infrastructure.JwtTokenProvider;
import finalmission.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController implements MemberControllerSwagger {

    private final MemberService memberService;
    private final JwtTokenProvider tokenProvider;
    private final CookieManager cookieManager;

    @GetMapping("/check")
    public MemberResponse check(@AuthenticationPrincipal Member member) {
        return new MemberResponse(member);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse signup(@RequestBody MemberSignupRequest request) {
        return memberService.signup(request);
    }

    @PostMapping("/login")
    public void login(@RequestBody MemberLoginRequest loginRequest, HttpServletResponse response) {
        Member member = memberService.validateLoginAndReturnMember(loginRequest);
        String token = tokenProvider.generateToken(member);
        cookieManager.setTokenCookie(response, token);
    }
}


