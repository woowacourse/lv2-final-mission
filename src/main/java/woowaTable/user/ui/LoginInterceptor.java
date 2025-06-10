package woowaTable.user.ui;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import woowaTable.common.exception.error.ForbiddenException;
import woowaTable.common.exception.error.UnauthorizedException;
import woowaTable.user.application.JwtHandler;
import woowaTable.user.application.TokenCookieService;
import woowaTable.user.domain.Role;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtHandler jwtHandler;
    private final TokenCookieService tokenCookieService;

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler
    ) {
        if (request.getCookies() == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        final String accessToken = tokenCookieService.getTokenFromCookies(request.getCookies());
        final String roleName = jwtHandler.decode(accessToken, JwtHandler.CLAIM_ROLE_KEY);

        final Role role = Role.valueOf(roleName);
        if (role != Role.OWNER) {
            throw new ForbiddenException("권한이 존재하지 않아 접근할 수 없습니다.");
        }
        return true;
    }
}
