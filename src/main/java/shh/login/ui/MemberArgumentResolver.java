package shh.login.ui;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shh.common.exception.UnauthorizedException;
import shh.login.application.JwtService;
import shh.login.application.TokenCookieService;
import shh.login.application.dto.LoginCheckRequest;

@Component
@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtService jwtService;
    private final TokenCookieService tokenCookieService;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(LoginCheckRequest.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) {
        final HttpServletRequest nativeRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        if (nativeRequest == null) {
            throw new UnauthorizedException("로그인이 필요합니다.");
        }
        final String token = tokenCookieService.getTokenFromCookies(nativeRequest.getCookies());
        final Map<String, String> decodedClaims = jwtService.decode(token);
        final Long id = Long.parseLong(decodedClaims.get(JwtService.CLAIM_ID_KEY));
        return new LoginCheckRequest(id);
    }
}
