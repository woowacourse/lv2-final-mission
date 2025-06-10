package finalmission.restaurant.dto;

import finalmission.restaurant.domain.Restaurant;

import java.util.List;

public record RestaurantResponse(
        Long id,
        String name,
        String address,
        List<String>menus
) {
    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getMenus());
    }
}
