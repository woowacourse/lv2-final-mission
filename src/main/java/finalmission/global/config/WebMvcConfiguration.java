package finalmission.global.config;

import finalmission.global.auth.interceptor.AuthorizationInterceptor;
import finalmission.global.auth.resolver.AuthenticationArgumentResolver;
import finalmission.global.auth.util.CookieUtil;
import finalmission.global.auth.util.JwtUtil;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    public WebMvcConfiguration(JwtUtil jwtUtil, CookieUtil cookieUtil) {
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(jwtUtil, cookieUtil))
                .addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver(jwtUtil, cookieUtil));
    }
}
