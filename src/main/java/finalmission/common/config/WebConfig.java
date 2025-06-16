package finalmission.common.config;

import finalmission.common.handler.CoachAuthorizeInterceptor;
import finalmission.common.handler.CrewAuthorizeInterceptor;
import finalmission.common.handler.LoginArgumentHandler;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginArgumentHandler loginArgumentHandler;
    private final CoachAuthorizeInterceptor coachAuthorizeInterceptor;
    private final CrewAuthorizeInterceptor crewAuthorizeInterceptor;

    public WebConfig(LoginArgumentHandler loginArgumentHandler,
        CoachAuthorizeInterceptor coachAuthorizeInterceptor,
        CrewAuthorizeInterceptor crewAuthorizeInterceptor) {
        this.loginArgumentHandler = loginArgumentHandler;
        this.coachAuthorizeInterceptor = coachAuthorizeInterceptor;
        this.crewAuthorizeInterceptor = crewAuthorizeInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginArgumentHandler);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(coachAuthorizeInterceptor)
            .addPathPatterns("/coaches/**")
            .addPathPatterns("/coach-reservations/**");
        registry.addInterceptor(crewAuthorizeInterceptor)
            .addPathPatterns("/crews/**")
            .addPathPatterns("/crew-reservations/**");
    }
}

