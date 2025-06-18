package finalmission.restaurant.controller.dto.response;

import finalmission.restaurant.domain.Restaurant;

public record RestaurantResponse(
        long id,
        String name,
        String description,
        String address,
        int zipcode,
        int tableCount
) {
    public static RestaurantResponse from(final Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getAddress(),
                restaurant.getZipcode(),
                restaurant.getTableCount()
        );
    }
}
