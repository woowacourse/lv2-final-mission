package finalmission.config;

import finalmission.config.Resolver.MemberInfoArgumentResolver;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final MemberInfoArgumentResolver memberInfoArgumentResolver;

    public WebConfiguration(final MemberInfoArgumentResolver memberInfoArgumentResolver) {
        this.memberInfoArgumentResolver = memberInfoArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberInfoArgumentResolver);
    }
}
