package finalmission.common.config;

import finalmission.auth.config.AuthenticationConfig;
import finalmission.auth.intercepter.AuthPreHandlerInterceptor;
import finalmission.auth.intercepter.AuthenticationPrincipalResolver;
import finalmission.auth.jwt.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
@Import(AuthenticationConfig.class)
public class WebConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalResolver(jwtTokenProvider));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthPreHandlerInterceptor(jwtTokenProvider))
                .addPathPatterns("/coach/**");
    }
}
