package finalmission.auth.config;

import finalmission.auth.interceptor.AuthorizationInterceptor;
import finalmission.auth.resolver.AuthorizationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final AuthorizationInterceptor authorizationInterceptor;
    private final AuthorizationArgumentResolver authorizationArgumentResolver;

    public AuthConfig(AuthorizationInterceptor authorizationInterceptor, AuthorizationArgumentResolver authorizationArgumentResolver) {
        this.authorizationInterceptor = authorizationInterceptor;
        this.authorizationArgumentResolver = authorizationArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/signup");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authorizationArgumentResolver);
    }
}
