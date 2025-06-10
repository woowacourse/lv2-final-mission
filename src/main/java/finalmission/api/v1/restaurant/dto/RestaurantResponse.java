package finalmission.api.v1.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.api.v1.restaurant.domain.Restaurant;
import java.time.LocalTime;

public record RestaurantResponse(
        Long id,
        String name,
        Long reservationCapacity,
        String address,
        @JsonFormat(pattern = "HH:mm") LocalTime openTime,
        @JsonFormat(pattern = "HH:mm") LocalTime closingTime
) {
    public RestaurantResponse(final Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getReservationCapacity(), restaurant.getAddress(), restaurant.getOpenTime(), restaurant.getClosingTime());
    }
}
