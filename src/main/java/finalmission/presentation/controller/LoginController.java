package finalmission.presentation.controller;

import finalmission.application.MemberService;
import finalmission.domain.Member;
import finalmission.exception.AuthenticationException;
import finalmission.exception.AuthorizationException;
import finalmission.infrastructure.JwtTokenProvider;
import finalmission.presentation.dto.CheckLoginRequest;
import finalmission.presentation.dto.LoginRequest;
import finalmission.presentation.dto.MemberDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
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
    public ResponseEntity<MemberDto> checkLogin(@RequestBody CheckLoginRequest loginRequest, HttpServletRequest request) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationException("유효하지 않은 토큰입니다."));
        String payload = jwtTokenProvider.getPayload(token);
        long memberId = Long.parseLong(payload);

        if (memberId == loginRequest.id()) {
            Member member = memberService.getById(memberId);
            return ResponseEntity.ok(new MemberDto(member.getId(), member.getName()));
        }
        throw new AuthorizationException("인가되지 않은 사용자입니다.");
    }
}
