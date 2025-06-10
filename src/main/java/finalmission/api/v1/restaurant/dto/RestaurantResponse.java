package finalmission.api.v1.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.api.v1.restaurant.domain.Restaurant;
import java.time.LocalTime;
import java.util.List;

public record RestaurantResponse(
        Long id,
        String name,
        List<ReservationCapacityResponse> reservationCapacities,
        String address,
        @JsonFormat(pattern = "HH:mm") LocalTime openTime,
        @JsonFormat(pattern = "HH:mm") LocalTime closingTime
) {
    public RestaurantResponse(final Restaurant restaurant) {
        this(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getReservationCapacities().stream().map(ReservationCapacityResponse::new).toList(),
                restaurant.getAddress(),
                restaurant.getOpenTime(),
                restaurant.getClosingTime());
    }
}
