package finalmission.auth;

import java.util.Arrays;
import java.util.Objects;
import finalmission.exception.AuthNotExistsCookieException;
import finalmission.exception.AuthNotValidTokenException;
import finalmission.member.domain.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@AllArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String TOKEN_NAME = "token";

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String token = extractToken(webRequest);
        validateToken(token);

        final String phoneNumber = jwtProvider.extractSubject(token);
        return new Member(phoneNumber);
    }

    private String extractToken(final NativeWebRequest request) {
        final HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        final Cookie[] cookies = nativeRequest.getCookies();
        validateExistsCookies(cookies);

        return Arrays.stream(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), TOKEN_NAME))
                .map(Cookie::getValue)
                .findAny()
                .orElseThrow(AuthNotExistsCookieException::new);
    }

    private void validateToken(final String token) {
        if(!jwtProvider.isValidToken(token)){
            throw new AuthNotValidTokenException();
        }
    }

    private void validateExistsCookies(final Cookie[] cookies) {
        if(cookies == null){
            throw new AuthNotExistsCookieException();
        }
    }
}
