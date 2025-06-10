package finalmission.common.resolver;

import finalmission.common.jwt.JwtTokenProvider;
import finalmission.member.dto.LoginInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class)
                && parameter.getParameterType().equals(LoginInfo.class);
    }

    @Override
    public LoginInfo resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                     final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = jwtTokenProvider.extractTokenFromCookie(request.getCookies());
        long memberId = jwtTokenProvider.getMemberId(token);
        return new LoginInfo(memberId);
    }
}
