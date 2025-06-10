package finalmission.restaurant.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RestaurantRequest(
        @NotBlank String name,
        @NotNull String description,
        @NotBlank String address,
        @NotNull int zipcode,
        @NotNull int tableCount
) {
}
