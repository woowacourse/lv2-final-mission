package finalmission.dto.layer;

import finalmission.dto.request.LoginRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginContent(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password
) {

    public LoginContent(LoginRequest loginRequest) {
        this(loginRequest.email(), loginRequest.password());
    }
}
