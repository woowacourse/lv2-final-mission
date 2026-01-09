package finalmission.auth.config;

import finalmission.auth.AuthRequired;
import finalmission.auth.AuthToken;
import finalmission.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    private boolean isAuthenticationNotRequired(final Object handler) {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        AuthRequired authRequired = handlerMethod.getMethodAnnotation(AuthRequired.class);
        return authRequired == null;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isAuthenticationNotRequired(handler)) {
            return true;
        }
        AuthToken authToken = AuthToken.extract(request);
        Long memberId = jwtUtil.validateAndResolveToken(authToken);
        request.setAttribute("authorization", memberId);
        return true;
    }
}
