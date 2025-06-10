package finalmission.restaurant.ui.dto;

public record CreateRestaurantRequest(
        String name,
        String description,
        String place,
        String phoneNumber
) {

}
