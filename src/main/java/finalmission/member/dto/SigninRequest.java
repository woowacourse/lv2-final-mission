package finalmission.member.dto;

import jakarta.validation.constraints.NotBlank;

public record SigninRequest(
        @NotBlank String email,
        @NotBlank String password
) {
}
