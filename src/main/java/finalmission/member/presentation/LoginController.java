package finalmission.member.presentation;

import finalmission.common.resolver.LoginMember;
import finalmission.member.dto.LoginInfo;
import finalmission.member.dto.LoginRequest;
import finalmission.member.dto.MemberResponse;
import finalmission.member.service.AuthService;
import finalmission.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequestDto, HttpServletResponse response) {
        String token = authService.publishLoginToken(loginRequestDto);
        Cookie cookie = createCookie(token);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    @GetMapping("/login/check")
    public ResponseEntity<MemberResponse> checkLogin(@LoginMember LoginInfo loginMember) {
        MemberResponse memberResponse = memberService.findMemberById(loginMember.memberId());
        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        Cookie cookie = createCookie(null);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
