package finalmission.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer {

    private final OwnerHandlerInterceptor ownerHandlerInterceptor;
    private final MemberClaimArgumentResolver memberClaimArgumentResolver;

    public MvcConfigurer(
            OwnerHandlerInterceptor ownerHandlerInterceptor,
            MemberClaimArgumentResolver memberClaimArgumentResolver
    ) {
        this.ownerHandlerInterceptor = ownerHandlerInterceptor;
        this.memberClaimArgumentResolver = memberClaimArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ownerHandlerInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberClaimArgumentResolver);
    }
}
