package finalmission.global.auth.interceptor;

import finalmission.global.auth.annotation.RoleRequired;
import finalmission.global.auth.util.JwtUtil;
import finalmission.member.entity.RoleType;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_PREFIX = "Bearer";

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        RoleRequired roleRequired = handlerMethod.getMethodAnnotation(RoleRequired.class);
        if (roleRequired == null) {
            return true;
        }

        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(AUTHORIZATION_PREFIX)) {
            // TODO: 커스텀 예외로 변경
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        String token = header.substring(7).trim();
        Claims claims = jwtUtil.parseToken(token);
        RoleType requestedRole = RoleType.valueOf(claims.get("role", String.class));
        if (Arrays.stream(roleRequired.roleType()).noneMatch(requiredRole -> requiredRole == requestedRole)) {
            // TODO: 커스텀 예외로 변경
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        return true;
    }
}
