package finalmission.login.authentication;

import finalmission.login.authorization.handler.AuthorizationHandler;
import finalmission.login.authorization.handler.TokenCookieHandler;
import finalmission.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginAuthenticationResolver implements HandlerMethodArgumentResolver {
    private final AuthorizationHandler<String> authorizationHandler;
    private final MemberService memberService;

    public LoginAuthenticationResolver(
            MemberService memberService,
            TokenCookieHandler tokenCookieHandler
    ) {
        this.memberService = memberService;
        this.authorizationHandler = tokenCookieHandler;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String token = authorizationHandler.extractToken(httpServletRequest);
        return memberService.findByToken(token);
    }
}
