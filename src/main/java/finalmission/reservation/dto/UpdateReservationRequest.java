package finalmission.reservation.dto;

import java.time.LocalDateTime;

public record UpdateReservationRequest(
        LocalDateTime reservationDateTime,
        int personnel
) {
}
