package finalmission.config;

import finalmission.auth.CookieManager;
import finalmission.auth.JwtTokenProvider;
import finalmission.auth.LoginMemberInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final CookieManager cookieManager;
    private final JwtTokenProvider jwtTokenProvider;


    public WebMvcConfiguration(final CookieManager cookieManager, final JwtTokenProvider jwtTokenProvider) {
        this.cookieManager = cookieManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberInterceptor(jwtTokenProvider, cookieManager));
    }
}
