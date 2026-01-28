package finalmission.auth.resolver;

import finalmission.auth.extractor.CookieExtractor;
import finalmission.auth.provider.JwtTokenProvider;
import finalmission.exception.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthorizationArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieExtractor cookieExtractor;

    public AuthorizationArgumentResolver(JwtTokenProvider jwtTokenProvider, CookieExtractor cookieExtractor) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieExtractor = cookieExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticated.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = cookieExtractor.extract(httpServletRequest)
                .orElseThrow(() -> new AuthException("인증 정보가 올바르지 않습니다"));
        String subject = jwtTokenProvider.extractSubject(token);
        try {
            return new MemberPrincipal(Long.parseLong(subject));
        } catch (NumberFormatException e) {
            throw new AuthException("토큰 정보가 올바르지 않습니다");
        }
    }
}
