package finalmission.presentation.controller;

import finalmission.application.MemberService;
import finalmission.infrastructure.JwtTokenProvider;
import finalmission.presentation.dto.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        long memberId = memberService.getIdBy(request.email(), request.password());
        String token = jwtTokenProvider.createToken(String.valueOf(memberId));

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(300000);
        cookie.setSecure(true);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
