package finalmission.common.config;

import finalmission.common.interceptor.AuthorizationInterceptor;
import finalmission.member.auth.MemberInfoArgumentResolver;
import finalmission.member.auth.util.JwtExtractor;
import finalmission.member.service.AuthService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthService authService;
    private final JwtExtractor jwtExtractor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MemberInfoArgumentResolver(jwtExtractor, authService));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(jwtExtractor, authService))
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/login",
                        "/signup",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/webjars/**"
                );
    }
}
