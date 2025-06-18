package finalmission.auth;

import finalmission.auth.dto.LoginMember;
import finalmission.auth.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {

    private final AuthService authService;

    public Interceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Cookie[] cookies = request.getCookies();
        String token = extractTokenFromCookie(cookies)
                .orElseThrow(() -> new IllegalArgumentException("token 쿠키가 없습니다."));
        LoginMember loginMember = authService.findAuthenticatedMember(token);

        if (loginMember == null) {
            response.setStatus(401);
            return false;
        }

        return true;
    }

    private Optional<String> extractTokenFromCookie(Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> "token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}
