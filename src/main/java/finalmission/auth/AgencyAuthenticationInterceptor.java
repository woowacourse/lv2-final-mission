package finalmission.auth;

import finalmission.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@RequiredArgsConstructor
public class AgencyAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {

        String token = authorizationExtractor.extract(request)
                .orElseThrow(() -> new IllegalArgumentException("Token is missing"));

        if (token.isEmpty()) {
            response.sendRedirect("/");
            return false;
        }

        String role = jwtTokenProvider.getTokenInfo(token).role();

        log.info("Role: {}", role);
        if (!"AGENCY".equals(role)) {
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}
