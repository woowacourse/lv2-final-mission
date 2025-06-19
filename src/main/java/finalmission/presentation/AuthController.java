package finalmission.presentation;

import finalmission.domain.service.MemberService;
import finalmission.domain.service.dto.LoginRequest;
import finalmission.domain.service.dto.SignUpRequest;
import finalmission.infrastructure.RandomNameClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final MemberService memberService;
    private final RandomNameClientService randomNameClientService;

    public AuthController(MemberService memberService, RandomNameClientService randomNameClientService) {
        this.memberService = memberService;
        this.randomNameClientService = randomNameClientService;
    }

    @PostMapping("/signup")
    public void signup(@RequestBody LoginRequest request) {
        List<String> surnames = randomNameClientService.generate();
        SignUpRequest signUpRequest = new SignUpRequest(request.email(), request.password(), surnames.get(0));
        memberService.registerMember(signUpRequest);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        String token = memberService.publishLoginToken(loginRequest);
        Cookie cookie = createCookie(token);
        response.addCookie(cookie);
    }

    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
