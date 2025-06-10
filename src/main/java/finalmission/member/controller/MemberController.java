package finalmission.member.controller;

import finalmission.member.service.MemberService;
import finalmission.member.service.dto.CreateMemberRequest;
import finalmission.member.service.dto.LoginRequest;
import finalmission.member.service.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid CreateMemberRequest request) {
        memberService.create(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = memberService.login(request);
        ResponseCookie cookie = ResponseCookie.from(response.token())
                .httpOnly(true)
                .secure(true)
                .maxAge(3600000)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
