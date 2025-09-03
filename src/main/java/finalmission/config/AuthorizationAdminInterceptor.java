package finalmission.config;

import finalmission.domain.Role;
import finalmission.exception.AccessDeniedException;
import finalmission.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationAdminInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider tokenProvider;

    public AuthorizationAdminInterceptor(final JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
            final Object handler) throws Exception {
        final Cookie[] cookies = request.getCookies();
        final String token = tokenProvider.extractTokenFromCookie(cookies);
        final Role role = tokenProvider.extractRole(token);
        if (!Role.isAdmin(role)) {
            throw new AccessDeniedException("관리자 권한이 필요합니다");
        }
        return true;
    }
}
