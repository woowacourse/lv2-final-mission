package finalmission.controller;

import finalmission.dto.request.LoginRequest;
import finalmission.entity.Member;
import finalmission.service.AuthService;
import finalmission.service.MemberService;
import finalmission.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    public AuthController(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @RequestBody LoginRequest request,
            HttpServletResponse response) {
        Member member = memberService.findByEmailAndPassword(request);
        String accessToken = authService.createTokenByMember(member);

        CookieUtil.addCookie("token", accessToken, response);

        return ResponseEntity.ok().build();
    }
}
