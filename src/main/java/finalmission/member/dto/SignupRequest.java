package finalmission.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(
        @Email
        String email,
        @NotNull
        String password
) {

}
