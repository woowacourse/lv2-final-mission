package finalmission.member.controller;

import finalmission.member.controller.dto.request.LoginRequest;
import finalmission.member.controller.dto.request.SignUpRequest;
import finalmission.member.controller.dto.response.SignUpResponse;
import finalmission.member.service.MemberService;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final int cookieMaxAge;

    public MemberController(
            MemberService memberService,
            @Value("${auth.cookie.max-age}") int cookieMaxAge
    ) {
        this.memberService = memberService;
        this.cookieMaxAge = cookieMaxAge;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse response = memberService.signup(signUpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        String token = memberService.login(loginRequest);
        ResponseCookie cookie = ResponseCookie.from("token")
                .value(token)
                .httpOnly(true)
                .maxAge(cookieMaxAge)
                .path("/")
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("token")
                .httpOnly(true)
                .maxAge(0)
                .path("/")
                .build();
        return ResponseEntity.ok().header(org.springframework.http.HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
