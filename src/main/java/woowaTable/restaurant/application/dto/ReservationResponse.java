package woowaTable.restaurant.application.dto;

import java.time.LocalDateTime;
import woowaTable.restaurant.domain.Reservation;
import woowaTable.restaurant.domain.Restaurant;

public record ReservationResponse(
        Long id,
        LocalDateTime dateTime,
        Restaurant restaurant
) {

    public static ReservationResponse from(final Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getDateTime(),
                reservation.getRestaurant()
        );
    }
}
