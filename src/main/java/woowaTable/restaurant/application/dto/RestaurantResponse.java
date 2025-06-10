package woowaTable.restaurant.application.dto;

import woowaTable.restaurant.domain.Restaurant;

public record RestaurantResponse(
        String name,
        String region
) {

    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getName().getValue(),
                restaurant.getRegion().name()
        );
    }
}
