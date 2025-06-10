package finalmission.presentation.support.methodresolver;

import finalmission.infrastructure.JwtPayload;
import finalmission.infrastructure.JwtProvider;
import finalmission.presentation.support.JwtTokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final JwtTokenExtractor jwtTokenExtractor;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AuthInfo.class) &&
                parameter.hasParameterAnnotation(AuthPrincipal.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        JwtPayload jwtPayload = getJwtPayload(request);
        return new AuthInfo(jwtPayload.memberId(), jwtPayload.name(), jwtPayload.role());
    }

    private JwtPayload getJwtPayload(HttpServletRequest request) {
        String jwtToken = jwtTokenExtractor.extract(request);
        return jwtProvider.extractPayload(jwtToken);
    }
}
