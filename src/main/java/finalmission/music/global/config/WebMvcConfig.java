package finalmission.music.global.config;

import finalmission.music.global.auth.interceptor.AdminAuthInterceptor;
import finalmission.music.global.auth.interceptor.MemberAuthInterceptor;
import finalmission.music.global.auth.resolver.MemberArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final MemberAuthInterceptor memberAuthInterceptor;
    private final AdminAuthInterceptor adminAuthInterceptor;
    private final MemberArgumentResolver memberArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(memberAuthInterceptor)
            .addPathPatterns("/reservations/**")
            .addPathPatterns("/lotteries/**")
            .addPathPatterns("/logout");
        registry.addInterceptor(adminAuthInterceptor)
            .addPathPatterns("/albums/**")
            .addPathPatterns("/spotify/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberArgumentResolver);
    }
}
