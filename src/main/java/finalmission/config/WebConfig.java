package finalmission.config;

import finalmission.interceptor.RoleAuthInterceptor;
import finalmission.resolver.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final RoleAuthInterceptor roleAuthInterceptor;

    public WebConfig(LoginMemberArgumentResolver loginMemberArgumentResolver,
            RoleAuthInterceptor roleAuthInterceptor) {
        this.loginMemberArgumentResolver = loginMemberArgumentResolver;
        this.roleAuthInterceptor = roleAuthInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(roleAuthInterceptor);
    }
}