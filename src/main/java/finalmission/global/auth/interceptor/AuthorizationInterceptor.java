package finalmission.global.auth.interceptor;

import finalmission.global.auth.annotation.RoleRequired;
import finalmission.global.auth.util.CookieUtil;
import finalmission.global.auth.util.JwtUtil;
import finalmission.global.error.exception.ForbiddenException;
import finalmission.global.error.exception.UnauthorizedException;
import finalmission.member.entity.RoleType;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    public AuthorizationInterceptor(JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RoleRequired roleRequired = handlerMethod.getMethodAnnotation(RoleRequired.class);
        if (roleRequired == null) {
            return true;
        }

        RoleType[] requiredRoles = roleRequired.roleType();
        if (requiredRoles.length == 1 && requiredRoles[0] == RoleType.GUEST) {
            return true;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        String token = cookieUtil.extractValueFromCookie(cookies, "token");
        if (token.isEmpty() || !jwtUtil.validateToken(token)) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        RoleType role = RoleType.valueOf(jwtUtil.extractAllClaims(token).get("role", String.class));
        if (!Arrays.asList(roleRequired.roleType()).contains(role)) {
            throw new ForbiddenException("권한이 없습니다.");
        }

        return true;
    }
}
