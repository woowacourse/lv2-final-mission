package finalmission.member.controller;


import finalmission.login.authorization.handler.AuthorizationHandler;
import finalmission.login.authorization.handler.TokenCookieHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    private final AuthorizationHandler<String> authorizationHandler;

    public LogoutController(TokenCookieHandler tokenCookieHandler) {
        this.authorizationHandler = tokenCookieHandler;
    }

    @PostMapping
    public ResponseEntity<Void> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        authorizationHandler.deleteCookie(httpServletRequest, httpServletResponse);
        return ResponseEntity.ok().build();
    }
}
