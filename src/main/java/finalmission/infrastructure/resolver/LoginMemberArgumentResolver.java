package finalmission.infrastructure.resolver;

import finalmission.domain.Member;
import finalmission.domain.repository.MemberRepository;
import finalmission.infrastructure.security.AccessToken;
import finalmission.infrastructure.security.JwtProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
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
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String JWT_TOKEN_COOKIE_KEY = "token";

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class)
                && parameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return getMemberFromRequest(request);
    }

    private Member getMemberFromRequest(HttpServletRequest request) {
        Long memberId = getMemberIdFromRequest(request);
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("로그인이 필요합니다."));
    }

    private Long getMemberIdFromRequest(HttpServletRequest request) {
        try {
            AccessToken accessToken = extract(request);
            return jwtProvider.extractMemberId(accessToken);
        } catch (Exception e) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
    }

    public AccessToken extract(HttpServletRequest request) {
        return AccessToken.of(getTokenCookie(request).getValue());
    }

    private Cookie getTokenCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new IllegalArgumentException("인증 쿠키 값이 존재하지 않습니다.");
        }
        return Arrays.stream(request.getCookies())
                .filter(each -> each.getName().equals(JWT_TOKEN_COOKIE_KEY))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("인증 쿠키 값이 존재하지 않습니다."));
    }
}
