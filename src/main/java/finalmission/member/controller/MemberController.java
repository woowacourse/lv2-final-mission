package finalmission.member.controller;

import finalmission.member.dto.SignupRequest;
import finalmission.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<Void> signup(@RequestBody SignupRequest request, HttpServletResponse response) {
        Cookie cookie = memberService.addMember(request);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
