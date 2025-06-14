package finalmission.reservation.dto;

import java.time.LocalDateTime;

public record CreateReservationRequest(
        LocalDateTime reservationDateTime,
        Long restaurantId,
        int personnel
) {
}
