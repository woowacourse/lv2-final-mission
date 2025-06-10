package finalmission.infra.auth;

import finalmission.infra.jwt.JwtTokenExtractor;
import finalmission.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthenticationInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        try {
            String token = JwtTokenExtractor.extract(request);
            authService.findMemberByToken(token);
            return true;
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 인증되지 않은 사용자입니다.");
        }
    }
}
