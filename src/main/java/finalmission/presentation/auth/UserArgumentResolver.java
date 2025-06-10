package finalmission.presentation.auth;

import finalmission.application.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationService authenticationService;

    public UserArgumentResolver(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticated.class);
    }

    @Override
    public Object resolveArgument(
            @NonNull final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        AuthenticationTokenCookie tokenCookie = AuthenticationTokenCookie.fromRequest(request);

        if (tokenCookie.hasToken()) {
            return authenticationService.getCustomerByToken(tokenCookie.token());
        }

        throw new IllegalArgumentException("사용자 인증이 필요합니다.");
    }
}

