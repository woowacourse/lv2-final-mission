package finalmission.dto;

import finalmission.domain.restaurant.Restaurant;

public record RestaurantSimpleResponse(
        String name,
        String address,
        String description
) {

    public RestaurantSimpleResponse(final Restaurant restaurant) {
        this(restaurant.getName().getValue(),
                restaurant.getAddress().getValue(),
                restaurant.getDescription().getValue()
        );
    }
}
