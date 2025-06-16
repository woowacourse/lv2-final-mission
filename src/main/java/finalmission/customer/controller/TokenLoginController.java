package finalmission.customer.controller;

import finalmission.customer.controller.dto.request.TokenLoginCreateRequest;
import finalmission.customer.controller.dto.response.TokenLoginResponse;
import finalmission.customer.entity.Customer;
import finalmission.customer.resolver.LoginCustomer;
import finalmission.customer.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("login")
public class TokenLoginController {

    private final AuthService authService;

    public TokenLoginController(AuthService authService) {
        this.authService = authService;
    }

    @PutMapping
    public ResponseEntity<Void> tokenLogin(
            @RequestBody @Valid TokenLoginCreateRequest tokenLoginCreateRequest,
            HttpServletResponse response
    ) {
        TokenLoginResponse tokenLoginResponse = authService.tokenLogin(tokenLoginCreateRequest);
        Cookie cookie = new Cookie("token", tokenLoginResponse.token());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Customer> getMember(
            @LoginCustomer Customer customer
    ) {
        return ResponseEntity.ok().body(customer);
    }

}
