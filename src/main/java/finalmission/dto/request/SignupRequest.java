package finalmission.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password,
        String name
) {

    public SignupRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
