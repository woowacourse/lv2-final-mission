package finalmission.config.interceptor;

import finalmission.annotation.CheckRole;
import finalmission.domain.MemberRole;
import finalmission.exception.custom.CannotAccessException;
import finalmission.jwt.JwtTokenProvider;
import finalmission.util.CookieUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class CheckRoleHandlerInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public CheckRoleHandlerInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        CheckRole checkRole = handlerMethod.getMethodAnnotation(CheckRole.class);
        if (checkRole == null) {
            return true;
        }

        String token = CookieUtil.extractCookie(request, "token");
        Claims memberTokenInfo = jwtTokenProvider.extractToken(token);
        if (memberTokenInfo == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인이 필요한 서비스입니다.");
            return false;
        }
        String targetRole = memberTokenInfo.get("role", String.class);
        MemberRole[] roles = checkRole.value();
        if (Arrays.stream(roles).noneMatch(role -> role.name().equals(targetRole))) {
            throw new CannotAccessException("접근 불가한 유저입니다.");
        }
        return true;
    }
}
