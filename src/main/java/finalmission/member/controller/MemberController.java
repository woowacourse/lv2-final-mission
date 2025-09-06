package finalmission.member.controller;

import finalmission.member.dto.LoginRequest;
import finalmission.member.dto.SignupRequest;
import finalmission.member.dto.SignupResponse;
import finalmission.member.jwt.TokenCreateRequest;
import finalmission.member.jwt.TokenProvider;
import finalmission.member.service.MemberService;
import finalmission.util.CookieManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = memberService.signup(request);
        return ResponseEntity.created(URI.create("/login")).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request,
                                               HttpServletResponse response) {
        TokenCreateRequest tokenCreateRequest = memberService.login(request);
        String token = tokenProvider.createToken(tokenCreateRequest);
        Cookie cookie = CookieManager.setCookie("authorization", token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = CookieManager.expireCookie("authorization");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
