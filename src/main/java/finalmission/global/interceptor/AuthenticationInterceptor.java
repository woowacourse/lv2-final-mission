package finalmission.global.interceptor;

import finalmission.auth.exception.AuthException;
import finalmission.auth.service.AuthService;
import finalmission.external.jwt.JwtTokenExtractor;
import finalmission.member.domian.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
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
        String token = JwtTokenExtractor.extract(request);

        Role role = authService.findRoleByToken(token);
        if (role != Role.ADMIN) {
            throw new AuthException("[ERROR] 권한이 필요합니다.", HttpStatus.FORBIDDEN);
        }
        return true;
    }
}
