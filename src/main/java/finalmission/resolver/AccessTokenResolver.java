package finalmission.resolver;

import finalmission.annotation.AccessToken;
import finalmission.dto.layer.AccessTokenContent;
import finalmission.exception.UnauthorizedException;
import finalmission.utility.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AccessTokenResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    public AccessTokenResolver(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType() == AccessTokenContent.class ||
                parameter.hasParameterAnnotation(AccessToken.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String accessToken = findAccessTokenCookie(request);
        return jwtProvider.parseAccessToken(accessToken);
    }

    private String findAccessTokenCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new UnauthorizedException("쿠키가 존재하지 않습니다.");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("access")) {
                return cookie.getValue();
            }
        }
        throw new UnauthorizedException("인증 쿠키가 존재하지 않습니다.");
    }
}
