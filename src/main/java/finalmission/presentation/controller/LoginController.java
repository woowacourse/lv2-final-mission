package finalmission.presentation.controller;

import finalmission.application.MemberService;
import finalmission.domain.Member;
import finalmission.infrastructure.JwtTokenProvider;
import finalmission.presentation.AuthenticationElement;
import finalmission.presentation.dto.LoginRequest;
import finalmission.presentation.dto.MemberResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        long memberId = memberService.getIdBy(request.email(), request.password());
        String token = jwtTokenProvider.createToken(String.valueOf(memberId));

        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(300000);
        cookie.setSecure(true);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<MemberResponse> checkLogin(@AuthenticationElement Long memberId) {
        Member member = memberService.getById(memberId);
        return ResponseEntity.ok(new MemberResponse(member.getId(), member.getName()));
    }
}
