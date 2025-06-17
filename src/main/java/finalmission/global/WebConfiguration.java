package finalmission.global;

import finalmission.global.interceptor.LoginMemberInterceptor;
import finalmission.global.token.JwtTokenProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final JwtTokenProvider tokenProvider;

    public WebConfiguration(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginMemberInterceptor(tokenProvider))
                .addPathPatterns("/members/**");
    }
}
