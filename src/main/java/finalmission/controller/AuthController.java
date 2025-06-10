package finalmission.controller;

import finalmission.dto.LoginMemberCheckResponse;
import finalmission.dto.LoginMemberInfo;
import finalmission.dto.MemberLoginRequest;
import finalmission.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody MemberLoginRequest request) {
        log.info("회원 로그인 요청: email={}", request.email());
        ResponseCookie cookie = authService.loginWithCookie(request);

        log.info("회원 로그인 성공: email={}", request.email());
        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .build();
    }

    @GetMapping("/login/check")
    public ResponseEntity<LoginMemberCheckResponse> findLoginMember(final LoginMemberInfo info) {
        log.debug("회원 로그인 상태 확인 요청: memberId={}", info.id());
        LoginMemberCheckResponse response = new LoginMemberCheckResponse(info.name());

        log.debug("회원 로그인 확인 완료: memberName={}",response.name());
        return ResponseEntity.ok()
                .body(response);
    }
}
