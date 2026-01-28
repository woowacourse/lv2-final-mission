package finalmission.auth.interceptor;

import finalmission.auth.extractor.CookieExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final CookieExtractor cookieExtractor;

    public AuthorizationInterceptor(CookieExtractor cookieExtractor) {
        this.cookieExtractor = cookieExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<String> token = cookieExtractor.extract(request);
        if (token.isEmpty()) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
