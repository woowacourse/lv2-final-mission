package finalmission.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthorizationAdminInterceptor authorizationAdminInterceptor;
    private final AuthenticationArgumentResolver authenticationArgumentResolver;

    public WebMvcConfig(final AuthorizationAdminInterceptor authorizationAdminInterceptor,
            final AuthenticationArgumentResolver authenticationArgumentResolver) {
        this.authorizationAdminInterceptor = authorizationAdminInterceptor;
        this.authenticationArgumentResolver = authenticationArgumentResolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authorizationAdminInterceptor)
                .addPathPatterns("/admin/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationArgumentResolver);
    }
}
