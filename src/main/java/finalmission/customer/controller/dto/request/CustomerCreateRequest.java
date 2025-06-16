package finalmission.customer.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CustomerCreateRequest(
        @NotNull
        @NotBlank
        String name,

        @NotNull
        @NotBlank
        String email,

        @NotNull
        @NotBlank
        String password
) {
}
