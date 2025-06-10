package finalmission.common.config;

import finalmission.common.security.AuthorizationExtractor;
import finalmission.common.security.MemberInfoArgumentResolver;
import finalmission.common.security.RoleInterceptor;
import finalmission.common.security.JwtProvider;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthorizationExtractor authorizationExtractor;
    private final JwtProvider jwtProvider;

    public WebMvcConfig(
            AuthorizationExtractor authorizationExtractor,
            JwtProvider jwtProvider
    ) {
        this.authorizationExtractor = authorizationExtractor;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MemberInfoArgumentResolver(authorizationExtractor, jwtProvider));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RoleInterceptor(authorizationExtractor, jwtProvider));
    }
}
