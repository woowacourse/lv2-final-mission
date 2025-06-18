package finalmission.controller;

import finalmission.controller.config.CookieManager;
import finalmission.domain.Member;
import finalmission.infrastructure.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseCookie {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CookieManager cookieManager;

    public Cookie createFixtureCookie() {
        Member member = new Member(1L);
        String token = jwtTokenProvider.generateToken(member);
        return cookieManager.generateCookie(token);
    }
}
