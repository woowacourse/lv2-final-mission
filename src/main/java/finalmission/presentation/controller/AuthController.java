package finalmission.presentation.controller;

import finalmission.application.AuthenticationService;
import finalmission.domain.customer.Customer;
import finalmission.presentation.auth.Authenticated;
import finalmission.presentation.auth.AuthenticationTokenCookie;
import finalmission.presentation.request.LoginRequest;
import finalmission.presentation.response.CustomerResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(final AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public void login(
            @RequestBody @Valid final LoginRequest request,
            final HttpServletResponse response
    ) {
        String issuedToken = authenticationService.issueToken(request.email(), request.password());
        AuthenticationTokenCookie tokenCookie = AuthenticationTokenCookie.forResponse(issuedToken);
        response.addCookie(tokenCookie);
    }

    @GetMapping("/login/check")
    public CustomerResponse checkLogin(
            @Authenticated final Customer customer
    ) {
        return CustomerResponse.fromCustomer(customer);
    }

    @PostMapping("/logout")
    public void logout(
            final HttpServletResponse response
    ) throws IOException {
        AuthenticationTokenCookie tokenCookieForExpire = AuthenticationTokenCookie.forExpire();
        response.addCookie(tokenCookieForExpire);
        response.sendRedirect("/");
    }
}
