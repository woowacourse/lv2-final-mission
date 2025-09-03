package finalmission.config;

import finalmission.auth.AgencyAuthenticationInterceptor;
import finalmission.auth.AuthenticationArgumentResolver;
import finalmission.auth.AdminAuthenticationInterceptor;
import finalmission.auth.AuthorizationExtractor;
import finalmission.util.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new AdminAuthenticationInterceptor(jwtTokenProvider, authorizationExtractor))
                .addPathPatterns("/admin/**");
        registry.addInterceptor(new AgencyAuthenticationInterceptor(jwtTokenProvider, authorizationExtractor))
                .addPathPatterns("/agency/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver(jwtTokenProvider, authorizationExtractor));
    }
}
