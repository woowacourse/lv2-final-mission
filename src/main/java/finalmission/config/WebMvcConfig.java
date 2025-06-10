package finalmission.config;

import finalmission.auth.JwtTokenProvider;
import finalmission.member.repository.MemberRepository;
import finalmission.member.service.MemberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MemberArgumentResolver(new MemberService(memberRepository, tokenProvider)));
    }
}
