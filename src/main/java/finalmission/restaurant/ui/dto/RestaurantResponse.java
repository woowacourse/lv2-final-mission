package finalmission.restaurant.ui.dto;

import finalmission.restaurant.domain.Restaurant;
import lombok.Builder;

@Builder
public record RestaurantResponse(
        Long id,
        String name,
        String description,
        String place,
        String phoneNumber
) {
    public static RestaurantResponse from(final Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .place(restaurant.getPlace())
                .phoneNumber(restaurant.getPhoneNumber())
                .build();
    }
}
