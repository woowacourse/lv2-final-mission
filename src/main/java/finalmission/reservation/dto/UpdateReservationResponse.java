package finalmission.reservation.dto;

import java.time.LocalDateTime;

public record UpdateReservationResponse(
        LocalDateTime reservationDateTime,
        int personnel
) {
}
