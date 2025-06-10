package finalmission.restaurant.ui.dto;

import finalmission.restaurant.domain.Restaurant;

public record RestaurantResponse(
        Long id,
        String name,
        String description,
        String place,
        String phoneNumber
) {
    public static RestaurantResponse from(final Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getPlace(),
                restaurant.getPhoneNumber()
        );
    }
}
