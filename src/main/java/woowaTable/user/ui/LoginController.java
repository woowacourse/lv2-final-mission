package woowaTable.user.ui;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import woowaTable.user.application.LoginService;
import woowaTable.user.application.TokenCookieService;
import woowaTable.user.application.dto.LoginRequest;
import woowaTable.user.application.dto.Token;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final TokenCookieService tokenCookieService;

    @Value("${security.jwt.token.access.expire-length}")
    private long expiration;

    @PostMapping("/login/customer")
    public ResponseEntity<Void> customerlogin(@Valid @RequestBody final LoginRequest request) {
        final Token token = loginService.customerLogin(request);
        final String cookie = tokenCookieService.createTokenCookie(token.accessToken(), expiration);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie)
                .build();
    }

    @PostMapping("/login/owner")
    public ResponseEntity<Void> ownerlogin(@Valid @RequestBody final LoginRequest request) {
        final Token token = loginService.ownerLogin(request);
        final String cookie = tokenCookieService.createTokenCookie(token.accessToken(), expiration);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie)
                .build();
    }
}
