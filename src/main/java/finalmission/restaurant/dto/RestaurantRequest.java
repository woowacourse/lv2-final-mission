package finalmission.restaurant.dto;

import finalmission.restaurant.domain.Restaurant;

import java.util.List;

public record RestaurantRequest(
        String name,
        String address,
        List<String> menus
) {
    public Restaurant toRestaurantEntity() {
        return new Restaurant(null, name, address, menus);
    }
}
