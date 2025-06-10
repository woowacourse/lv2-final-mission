package finalmission.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Arrays;

@Component
public class LoginMemberInterceptor implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginMemberInterceptor(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer, final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return new IllegalArgumentException("쿠키 오류");
        }
        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("token"))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        Long memberId = jwtTokenProvider.extractId(cookie.getValue());
        return new LoginMemberInfo(memberId);
    }
}
