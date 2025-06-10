package finalmission.presentation.controller;

import finalmission.dto.LoginRequestDto;
import finalmission.service.AuthService;
import finalmission.support.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtils cookieUtils;

    @PostMapping("/login")
    public void login(HttpServletResponse httpServletResponse, LoginRequestDto loginRequestDto) {
        String token = authService.login(loginRequestDto);
        cookieUtils.setCookieForToken(httpServletResponse, token);
    }
}
