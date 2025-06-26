package finalmission.general.auth.resolver;

import finalmission.general.auth.model.LoginInfo;
import finalmission.general.auth.util.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    public LoginArgumentResolver(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(LoginInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase("token")) {
                String accessToken = cookie.getValue();
                return new LoginInfo(jwtProvider.getUsername(accessToken));
            }
        }
        throw new IllegalStateException("로그인되지 않은 상태이거나 잘못된 유저 정보입니다.");
    }
}
