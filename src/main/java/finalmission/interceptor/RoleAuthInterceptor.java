package finalmission.interceptor;

import finalmission.annotation.CheckRole;
import finalmission.common.Role;
import finalmission.dto.request.LoginMemberRequest;
import finalmission.exception.UnauthorizedException;
import finalmission.service.AuthService;
import finalmission.jwt.JwtExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class RoleAuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public RoleAuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        CheckRole checkRole = handlerMethod.getMethodAnnotation(CheckRole.class);

        if (checkRole == null) {
            checkRole = handlerMethod.getBeanType().getAnnotation(CheckRole.class);
        }

        if (checkRole == null) {
            return true;
        }

        try {
            String token = JwtExtractor.extractFromRequest(request);
            LoginMemberRequest loginMember = authService.getLoginMemberByToken(token);

            Role[] requiredRoles = checkRole.value();
            boolean hasRequiredRole = Arrays.stream(requiredRoles)
                    .anyMatch(role -> loginMember.role() == role);

            if (!hasRequiredRole) {
                throw new UnauthorizedException("권한이 없습니다. 필요한 권한: " + Arrays.toString(requiredRoles));
            }

            return true;
        } catch (Exception e) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }
}
