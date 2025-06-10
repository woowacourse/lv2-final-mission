package woowaTable.common.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final HandlerMethodArgumentResolver userArgumentResolver;
    private final HandlerInterceptor adminInterceptor;

    public WebMvcConfig(
            final HandlerMethodArgumentResolver userArgumentResolver,
            final HandlerInterceptor adminInterceptor
    ) {
        this.userArgumentResolver = userArgumentResolver;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/owner/**");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userArgumentResolver);
    }
}
