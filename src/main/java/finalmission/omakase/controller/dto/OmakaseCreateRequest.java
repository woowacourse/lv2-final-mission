package finalmission.omakase.controller.dto;

import finalmission.omakase.entity.Rating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OmakaseCreateRequest(
        @NotNull
        @NotBlank
        String name,

        @NotNull
        Rating rating
) {
}
