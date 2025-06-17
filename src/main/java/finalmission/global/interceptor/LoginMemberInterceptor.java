package finalmission.global.interceptor;

import static finalmission.global.token.TokenUtils.extractTokenFromCookie;

import finalmission.global.exception.UnauthorizedException;
import finalmission.global.token.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginMemberInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider tokenProvider;

    public LoginMemberInterceptor(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = extractTokenFromCookie(request);

        if (token == null) {
            throw new UnauthorizedException("토큰이 존재하지 않음");
        }

        if (!tokenProvider.validateToken(token)) {
            throw new UnauthorizedException("유효하지 않는 토큰");
        }

        Claims claims = tokenProvider.getClaims(token);
        String roleText = (String) claims.get("role");

        if (!roleText.equals("member")) {
            return false;
        }
        return true;
    }
}
