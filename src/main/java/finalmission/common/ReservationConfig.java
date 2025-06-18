package finalmission.common;

import finalmission.member.service.LoginService;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ReservationConfig implements WebMvcConfigurer {

    private final LoginService loginService;
    private final TokenCookieManager tokenCookieManager;
    private final JwtTokenProvider tokenProvider;

    public ReservationConfig(LoginService loginService, TokenCookieManager tokenCookieManager,
                             JwtTokenProvider tokenProvider) {
        this.loginService = loginService;
        this.tokenCookieManager = tokenCookieManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver(loginService, tokenCookieManager));
    }
}
