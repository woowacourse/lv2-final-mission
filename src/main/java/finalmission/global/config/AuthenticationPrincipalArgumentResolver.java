package finalmission.global.config;

import static finalmission.global.exception.ErrorMessage.INTERNAL_SERVER_ERROR;

import finalmission.controller.config.CookieManager;
import finalmission.domain.Member;
import finalmission.infrastructure.JwtTokenProvider;
import finalmission.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final CookieManager cookieManager;
    private final JwtTokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isAuthenticationAnnotation = parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
        boolean isMemberParameter = Member.class.isAssignableFrom(parameter.getParameterType());
        return isAuthenticationAnnotation && isMemberParameter;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String token = cookieManager.extractToken(request);
        Long memberId = tokenProvider.extractMemberId(token);

        return memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.error("[멤버 인자] 존재하지 않는 회원 조회 - memberId: {}", memberId);
                    return new RuntimeException(INTERNAL_SERVER_ERROR.get());
                });
    }
}
