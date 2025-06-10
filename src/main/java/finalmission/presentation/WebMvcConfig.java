package finalmission.presentation;

import finalmission.infrastructure.TokenProvider;
import finalmission.presentation.auth.AuthenticationArgumentResolver;
import finalmission.presentation.auth.AuthorizationExtractor;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenProvider tokenProvider;
    private final AuthorizationExtractor authorizationExtractor;

    public WebMvcConfig(
            TokenProvider tokenProvider,
            AuthorizationExtractor authorizationExtractor
    ) {
        this.tokenProvider = tokenProvider;
        this.authorizationExtractor = authorizationExtractor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationArgumentResolver(tokenProvider, authorizationExtractor));
    }
}
