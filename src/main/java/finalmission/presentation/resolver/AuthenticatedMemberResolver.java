package finalmission.presentation.resolver;

import finalmission.repository.MemberRepository;
import finalmission.service.AuthenticatedMember;
import finalmission.support.CookieUtils;
import finalmission.support.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticatedMemberResolver implements HandlerMethodArgumentResolver {

    private final CookieUtils cookieUtils;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = cookieUtils.getToken(request);

        String payload = jwtTokenProvider.getPayload(token);
        return memberRepository.findById(Long.parseLong(payload))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자에 대한 요청입니다."));
    }
}
