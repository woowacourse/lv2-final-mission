package finalmission.music.auth.controller;

import finalmission.music.auth.controller.dto.LoginRequest;
import finalmission.music.global.auth.LoginMember;
import finalmission.music.member.domain.Member;
import finalmission.music.auth.service.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody final LoginRequest loginRequest, final HttpServletRequest request) {
        Member loginMember = loginService.login(loginRequest);

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(180000);
        session.setAttribute("loginMember", loginMember);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(final HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }
}
