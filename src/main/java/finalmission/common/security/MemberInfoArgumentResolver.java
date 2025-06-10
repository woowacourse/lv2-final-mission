package finalmission.common.security;

import finalmission.member.domain.MemberInfo;
import finalmission.member.domain.MemberRole;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthorizationExtractor authorizationExtractor;
    private final JwtProvider jwtProvider;

    public MemberInfoArgumentResolver(final AuthorizationExtractor authorizationExtractor,
                                      final JwtProvider jwtProvider) {
        this.authorizationExtractor = authorizationExtractor;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberInfo.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory)
            throws Exception {
        String token = authorizationExtractor.extract(webRequest);
        long memberId = Long.parseLong(jwtProvider.getSubject(token));
        MemberRole role = MemberRole.valueOf(jwtProvider.getClaim(token, "role"));
        return new MemberInfo(memberId, role);
    }
}
