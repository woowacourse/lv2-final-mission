package finalmission.restaurant.service;

import finalmission.restaurant.domain.Restaurant;

public record RestaurantResponse(
        Long id,
        String name
) {
    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(), restaurant.getName());
    }
}
