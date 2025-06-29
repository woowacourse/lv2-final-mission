package finalmission.auth.config;

import finalmission.auth.domain.AuthTokenExtractor;
import finalmission.auth.domain.AuthTokenProvider;
import finalmission.auth.ui.AuthRoleCheckInterceptor;
import finalmission.auth.ui.MemberAuthInfoArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AuthWebMvcConfig implements WebMvcConfigurer {

    private final AuthTokenExtractor<String> authTokenExtractor;
    private final AuthTokenProvider authTokenProvider;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(
                        new AuthRoleCheckInterceptor(authTokenExtractor, authTokenProvider)
                )
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/image/**", "/login", "/signup", "/");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MemberAuthInfoArgumentResolver(authTokenExtractor, authTokenProvider));
    }
}
