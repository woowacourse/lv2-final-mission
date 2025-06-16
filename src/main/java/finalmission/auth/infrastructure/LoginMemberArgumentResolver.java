package finalmission.auth.infrastructure;

import finalmission.auth.service.AuthService;
import finalmission.auth.controller.dto.request.LoginMember;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final Logger log = LoggerFactory.getLogger(LoginMemberArgumentResolver.class);

    private final AuthService authService;
    private final JwtTokenExtractor jwtTokenExtractor;

    public LoginMemberArgumentResolver(final AuthService authService, final JwtTokenExtractor jwtTokenExtractor) {
        this.authService = authService;
        this.jwtTokenExtractor = jwtTokenExtractor;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(LoginMember.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        final Cookie[] cookies = request.getCookies();
        validateTokenExists(cookies);
        String token = jwtTokenExtractor.extractTokenFromCookie(cookies);
        log.info(token);
        return authService.findLoginMemberByToken(token);
    }

    private void validateTokenExists(final Cookie[] cookies) {
        if (cookies == null || cookies.length == 0) {
            throw new IllegalArgumentException("토큰을 찾을 수 없습니다.");
        }
    }
}
