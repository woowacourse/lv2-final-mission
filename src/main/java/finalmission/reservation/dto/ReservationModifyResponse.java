package finalmission.reservation.dto;

import java.time.LocalDateTime;

public record ReservationModifyResponse(Long reservationId, LocalDateTime reservationTime) {
}
