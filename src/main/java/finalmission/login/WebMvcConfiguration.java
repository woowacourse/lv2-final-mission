package finalmission.login;

import finalmission.login.authentication.LoginAuthenticationResolver;
import finalmission.login.authorization.LoginAuthorizationInterceptor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private static final String ADMIN_ONLY_PATH = "/reservation";

    private final LoginAuthenticationResolver loginAuthenticationResolver;
    private final LoginAuthorizationInterceptor loginAuthorizationInterceptor;

    public WebMvcConfiguration(
            LoginAuthenticationResolver loginAuthenticationResolver,
            LoginAuthorizationInterceptor loginAuthorizationInterceptor
    ) {
        this.loginAuthenticationResolver = loginAuthenticationResolver;
        this.loginAuthorizationInterceptor = loginAuthorizationInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginAuthenticationResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthorizationInterceptor).addPathPatterns(ADMIN_ONLY_PATH);
    }
}
