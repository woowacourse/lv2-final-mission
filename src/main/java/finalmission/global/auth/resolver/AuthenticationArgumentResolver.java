package finalmission.global.auth.resolver;

import finalmission.global.auth.annotation.AuthenticationPrincipal;
import finalmission.global.auth.dto.LoginMember;
import finalmission.global.auth.util.CookieUtil;
import finalmission.global.auth.util.JwtUtil;
import finalmission.global.error.exception.UnauthorizedException;
import finalmission.member.entity.RoleType;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    public AuthenticationArgumentResolver(JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class)
                && parameter.getParameterType().equals(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        String token = cookieUtil.extractValueFromCookie(cookies, "token");
        if (token.isEmpty() || !jwtUtil.validateToken(token)) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }

        Claims claims = jwtUtil.extractAllClaims(token);

        Long id = claims.get("id", Long.class);
        String name = claims.get("name", String.class);
        RoleType role = RoleType.valueOf(claims.get("role", String.class));

        return new LoginMember(id, name, role);
    }
}
