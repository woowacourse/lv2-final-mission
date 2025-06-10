package finalmission.common.security;

import finalmission.member.domain.MemberRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class RoleInterceptor implements HandlerInterceptor {

    private static final String ADMIN = "/admin";

    private final AuthorizationExtractor authorizationExtractor;
    private final JwtProvider jwtProvider;

    public RoleInterceptor(final AuthorizationExtractor authorizationExtractor, final JwtProvider jwtProvider) {
        this.authorizationExtractor = authorizationExtractor;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        if (!(handler instanceof final HandlerMethod handlerMethod)) {
            return true;
        }
        String path = request.getRequestURI();
        if (path.startsWith(ADMIN)) {
            return validateToken(request, MemberRole.ADMIN);
        }
        return validateToken(request, handlerMethod);
    }

    private boolean validateToken(final HttpServletRequest request, final HandlerMethod handlerMethod) {
        RequireRole classAnnotation = handlerMethod.getBeanType().getAnnotation(RequireRole.class);
        RequireRole methodAnnotation = handlerMethod.getMethodAnnotation(RequireRole.class);
        if (classAnnotation != null) {
            return validateToken(request, classAnnotation.value());
        }
        if (methodAnnotation != null) {
            return validateToken(request, methodAnnotation.value());
        }
        return true;
    }

    private boolean validateToken(final HttpServletRequest request, final MemberRole memberRole) {
        String token = authorizationExtractor.extract(request);
        if (token == null) {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
        jwtProvider.validateJwtToken(token);
        MemberRole actualRole = MemberRole.from(jwtProvider.getClaim(token, "role"));
        if (memberRole == MemberRole.ADMIN && actualRole == MemberRole.REGULAR) {
            throw new IllegalArgumentException("접근할 수 없습니다.");
        }
        return true;
    }

}
