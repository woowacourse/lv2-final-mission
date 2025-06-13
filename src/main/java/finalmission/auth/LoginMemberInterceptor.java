package finalmission.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberInterceptor implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;
    private final CookieManager cookieManager;

    public LoginMemberInterceptor(final JwtTokenProvider jwtTokenProvider, final CookieManager cookieManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.cookieManager = cookieManager;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = cookieManager.extractTokenFromCookies(request.getCookies());
        Long id = jwtTokenProvider.extractId(token);
        return new LoginMemberInfo(id);
    }
}
