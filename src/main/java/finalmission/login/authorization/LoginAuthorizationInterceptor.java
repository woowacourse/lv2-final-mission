package finalmission.login.authorization;

import finalmission.login.authorization.handler.TokenCookieHandler;
import finalmission.member.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginAuthorizationInterceptor implements HandlerInterceptor {
    private final TokenCookieHandler tokenCookieHandler;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginAuthorizationInterceptor(
            TokenCookieHandler tokenCookieHandler,
            JwtTokenProvider jwtTokenProvider
    ) {
        this.tokenCookieHandler = tokenCookieHandler;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            Object handler
    ) {
        String token = tokenCookieHandler.extractToken(httpServletRequest);
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("토큰이 올바르지 않습니다");
        }
        String role = jwtTokenProvider.getPayloadRole(token);
        return role.equals(Role.ADMIN.getRole());
    }
}
