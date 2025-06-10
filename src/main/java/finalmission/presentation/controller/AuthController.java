package finalmission.presentation.controller;

import finalmission.dto.LoginRequestDto;
import finalmission.service.AuthService;
import finalmission.support.CookieUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 관련 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtils cookieUtils;

    @Operation(summary = "로그인 요청")
    @PostMapping("/login")
    public void login(HttpServletResponse httpServletResponse, LoginRequestDto loginRequestDto) {
        String token = authService.login(loginRequestDto);
        cookieUtils.setCookieForToken(httpServletResponse, token);
    }
}
