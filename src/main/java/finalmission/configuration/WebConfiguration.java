package finalmission.configuration;

import finalmission.resolver.AccessTokenResolver;
import finalmission.utility.JwtProvider;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;

    public WebConfiguration(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AccessTokenResolver(jwtProvider));
    }
}
