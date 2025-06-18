package finalmission.config;

import finalmission.config.Resolver.MemberInfoArgumentResolver;
import finalmission.config.interceptor.CheckRoleHandlerInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final MemberInfoArgumentResolver memberInfoArgumentResolver;
    private final CheckRoleHandlerInterceptor checkRoleHandlerInterceptor;

    public WebConfiguration(final MemberInfoArgumentResolver memberInfoArgumentResolver,
                            final CheckRoleHandlerInterceptor checkRoleHandlerInterceptor) {
        this.memberInfoArgumentResolver = memberInfoArgumentResolver;
        this.checkRoleHandlerInterceptor = checkRoleHandlerInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver);
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(checkRoleHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/signup", "/logout");
        ;
    }
}
