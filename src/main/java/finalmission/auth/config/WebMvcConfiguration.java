package finalmission.auth.config;

import finalmission.auth.Interceptor;
import finalmission.auth.Resolver;
import finalmission.auth.service.AuthService;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthService authService;

    public WebMvcConfiguration(final AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new Resolver(authService));
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor(authService))
                .addPathPatterns("/admin/**");
    }
}
