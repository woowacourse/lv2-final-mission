package finalmission.util;

import finalmission.domain.Member;
import finalmission.service.MemberService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import javax.crypto.SecretKey;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class MemberHandler implements HandlerMethodArgumentResolver {


    private final MemberService memberService;

    public MemberHandler(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        Cookie[] cookies = httpServletRequest.getCookies();
        Cookie foundCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName() == "tocken").findAny()
                .orElse(null);
        String jwt = foundCookie.getValue();
        String secret = "since-jjwt-api-0.11-secret-key-must-be-longer-than-32-bytes";
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        Long id = Long.valueOf(
                Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody().getSubject());

        Member member = memberService.findMemberById(id);
        return member;
    }
}
