package finalmission.controller;

import finalmission.dto.LoginInfo;
import finalmission.exception.MemberException;
import finalmission.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginLogoutController {

    @Autowired
    MemberService memberService;

    @PostMapping("/login")
    void login(@RequestBody LoginInfo loginInfo, HttpServletResponse httpServletResponse) {
        String token = memberService.loginMember(loginInfo);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        httpServletResponse.setStatus(200);
    }

    @GetMapping("/logout")
    void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = Arrays.stream(cookies).filter(cookie1 -> cookie1.getName() == "tocken").findAny()
                .orElseThrow(() -> {
                    throw new MemberException("잘못된 요청입니다.");
                });
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.setStatus(200);
    }
}
