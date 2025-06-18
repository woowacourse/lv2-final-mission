package finalmission.common.interceptor;

import finalmission.common.exception.AuthorizationException;
import finalmission.member.auth.annotation.PermitAll;
import finalmission.member.auth.annotation.RoleRequired;
import finalmission.member.auth.util.JwtExtractor;
import finalmission.member.auth.vo.MemberInfo;
import finalmission.member.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final JwtExtractor jwtExtractor;
    private final AuthService authService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        if (handlerMethod.hasMethodAnnotation(PermitAll.class)) {
            return true;
        }

        final String token = jwtExtractor.extractToken(request.getCookies());
        final MemberInfo memberInfo = authService.get(token);

        if (handlerMethod.hasMethodAnnotation(RoleRequired.class)) {
            RoleRequired roleRequired = handlerMethod.getMethodAnnotation(RoleRequired.class);
            if (Arrays.stream(roleRequired.value()).noneMatch(requiredRole -> requiredRole == memberInfo.role())) {
                throw new AuthorizationException("권한이 부족합니다.");
            }
        }

        return true;
    }
}
