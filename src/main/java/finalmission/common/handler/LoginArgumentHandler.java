package finalmission.common.handler;

import finalmission.domain.login.JwtProvider;
import finalmission.domain.login.MemberType;
import finalmission.domain.member.Member;
import finalmission.service.LoginService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginArgumentHandler implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;
    private final LoginService loginService;

    public LoginArgumentHandler(JwtProvider jwtProvider, LoginService loginService) {
        this.jwtProvider = jwtProvider;
        this.loginService = loginService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authHeader = request.getHeader("Authorization");
        String token = resolveToken(authHeader);
        Claims claims = jwtProvider.getClaimsAndValidateToken(token);
        Long id = Long.valueOf(claims.get("id").toString());
        String memberTypeStr = claims.get("memberType").toString();
        MemberType memberType = MemberType.valueOf(memberTypeStr);
        return getMember(id, memberType);
    }

    public Member getMember(Long id, MemberType memberType) {
        if (memberType.name().equals("COACH")) {
            return loginService.findByCoachId(id);
        }
        return loginService.findByCrewId(id);
    }

    private String resolveToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalStateException("Authorization 헤더가 잘못되었습니다.");
    }
}
