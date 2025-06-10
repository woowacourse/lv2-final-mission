package finalmission.auth.ui;

import finalmission.auth.domain.AuthRole;
import finalmission.auth.domain.AuthTokenExtractor;
import finalmission.auth.domain.AuthTokenProvider;
import finalmission.auth.domain.RequiresRole;
import finalmission.exception.auth.AuthenticationException;
import finalmission.exception.auth.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthRoleCheckInterceptor implements HandlerInterceptor {

    private final AuthTokenExtractor<String> authTokenExtractor;
    private final AuthTokenProvider authTokenProvider;

    @Override
    public boolean preHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            Object handler
    ) throws AuthenticationException {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        final RequiresRole requiresRole = extractRequiresRole(handlerMethod);
        if (requiresRole == null) {
            return true;
        }

        final String accessToken = authTokenExtractor.extract(request);
        if (!authTokenProvider.isValidToken(accessToken)) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }

        final AuthRole role = authTokenProvider.getRole(accessToken);
        if (Arrays.stream(requiresRole.authRoles())
                .noneMatch(authRole -> authRole == role)) {
            throw new AuthorizationException("권한이 부족합니다.");
        }
        return true;
    }

    private RequiresRole extractRequiresRole(final HandlerMethod handlerMethod) {
        RequiresRole requiresRole = handlerMethod.getMethodAnnotation(RequiresRole.class);

        if (requiresRole == null) {
            requiresRole = handlerMethod.getBeanType().getAnnotation(RequiresRole.class);
        }
        return requiresRole;
    }
}
