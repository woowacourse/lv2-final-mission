package finalmission.auth;

import finalmission.infrastructure.jwt.CookieTokenExtractor;
import finalmission.infrastructure.jwt.JwtTokenProvider;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenProvider tokenProvider;
    private final CookieTokenExtractor cookieTokenExtractor;

    public WebMvcConfig(final JwtTokenProvider tokenProvider, final CookieTokenExtractor cookieTokenExtractor) {
        this.tokenProvider = tokenProvider;
        this.cookieTokenExtractor = cookieTokenExtractor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver(tokenProvider, cookieTokenExtractor));
    }
}
