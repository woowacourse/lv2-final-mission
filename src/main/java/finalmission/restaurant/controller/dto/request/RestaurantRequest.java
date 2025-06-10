package finalmission.restaurant.controller.dto.request;

public record RestaurantRequest(
        String name,
        String description,
        String address,
        int zipcode,
        int tableCount
) {
}
