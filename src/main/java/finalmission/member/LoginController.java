package finalmission.member;

import finalmission.common.TokenCookieManager;
import finalmission.member.dto.LoginRequest;
import finalmission.member.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    private final TokenCookieManager tokenCookieManager;
    private final LoginService loginService;

    public LoginController(TokenCookieManager tokenCookieManager, LoginService loginService) {
        this.tokenCookieManager = tokenCookieManager;
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = loginService.loginAndReturnToken(request);
        tokenCookieManager.addTokenCookie(response, token);
        return ResponseEntity.ok().build();
    }
}
