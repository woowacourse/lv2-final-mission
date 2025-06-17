package finalmission.dto.restaurant;

import finalmission.domain.restaurant.Restaurant;

public record RestaurantSimpleResponse(
        long id,
        String name,
        String address,
        String description
) {

    public RestaurantSimpleResponse(final Restaurant restaurant) {
        this(restaurant.getId(),
                restaurant.getName().getValue(),
                restaurant.getAddress().getValue(),
                restaurant.getDescription().getValue()
        );
    }
}
