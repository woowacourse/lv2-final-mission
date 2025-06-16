package finalmission.customer.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TokenLoginCreateRequest(
        @NotBlank
        @NotNull
        String email,

        @NotBlank
        @NotNull
        String password
) {
}
