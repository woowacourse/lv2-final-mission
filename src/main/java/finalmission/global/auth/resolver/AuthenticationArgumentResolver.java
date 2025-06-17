package finalmission.global.auth.resolver;

import finalmission.global.auth.annotation.AuthenticationPrincipal;
import finalmission.global.auth.dto.LoginMember;
import finalmission.global.auth.util.JwtUtil;
import finalmission.member.entity.RoleType;
import io.jsonwebtoken.Claims;
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
public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_PREFIX = "Bearer";

    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class)
                && parameter.getParameterType().equals(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(AUTHORIZATION_PREFIX)) {
            // TODO: 커스텀 예외로 변경
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        String token = header.substring(7).trim();
        if (!jwtUtil.validateToken(token)) {
            // TODO: 커스텀 예외로 변경
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Claims claims = jwtUtil.parseToken(token);
        Long id = claims.get("id", Double.class).longValue();
        String name = claims.get("name", String.class);
        RoleType role = RoleType.valueOf(claims.get("role", String.class));

        return new LoginMember(id, name, role);
    }
}
