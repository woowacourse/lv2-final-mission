package finalmission.auth;

import finalmission.member.domain.vo.Role;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class OwnerHandlerInterceptor implements HandlerInterceptor {

    private final JwtTokenHandler jwtTokenHandler;

    public OwnerHandlerInterceptor(JwtTokenHandler jwtTokenHandler) {
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (!isTargetHandler((HandlerMethod) handler)) {
            return true;
        }

        validateRole(request);
        return true;
    }

    private void validateRole(HttpServletRequest request) throws IllegalAccessException {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                Role role = jwtTokenHandler.getRole(token);
                if (role == Role.OWNER) {
                    return ;
                }
                throw new IllegalAccessException("접근 권한이 없습니다.");
            }
        }
        throw new IllegalAccessException("로그인이 필요한 서비스 입니다.");
    }

    private boolean isTargetHandler(HandlerMethod handlerMethod) {
        return handlerMethod.hasMethodAnnotation(RequiredOwner.class);
    }
}
