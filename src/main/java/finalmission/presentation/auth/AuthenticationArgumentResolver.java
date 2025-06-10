package finalmission.presentation.auth;

import finalmission.exception.AuthenticationRequiredException;
import finalmission.exception.InvalidTokenException;
import finalmission.infrastructure.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenProvider tokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    public AuthenticationArgumentResolver(
            TokenProvider tokenProvider,
            AuthorizationExtractor authorizationExtractor
    ) {
        this.tokenProvider = tokenProvider;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(Authenticated.class) != null &&
                parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = authorizationExtractor.extract(httpServletRequest)
                .orElseThrow(AuthenticationRequiredException::new);
        try {
            return Long.parseLong(tokenProvider.extractMemberId(token));
        } catch (NumberFormatException e) {
            throw new InvalidTokenException();
        }
    }
}
