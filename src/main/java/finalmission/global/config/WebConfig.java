package finalmission.global.config;

import finalmission.customer.resolver.LoginCustomerArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginCustomerArgumentResolver loginCustomerArgumentResolver;

    public WebConfig(LoginCustomerArgumentResolver loginCustomerArgumentResolver) {
        this.loginCustomerArgumentResolver = loginCustomerArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginCustomerArgumentResolver);
    }
}
