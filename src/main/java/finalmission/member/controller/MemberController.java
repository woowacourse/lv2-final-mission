package finalmission.member.controller;

import finalmission.auth.JwtTokenProvider;
import finalmission.member.dto.LoginMemberRequest;
import finalmission.member.dto.MemberResponse;
import finalmission.member.dto.RegisterMemberRequest;
import finalmission.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberController(final MemberService memberService, final JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> register(@RequestBody RegisterMemberRequest memberRequest) {
        MemberResponse memberResponse = memberService.register(memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@RequestBody LoginMemberRequest memberRequest, HttpServletResponse response) {
        MemberResponse memberResponse = memberService.login(memberRequest);
        response.addCookie(new Cookie("token", jwtTokenProvider.createToken(memberResponse.id())));
        return ResponseEntity.status(HttpStatus.OK).body(memberResponse);
    }
}
