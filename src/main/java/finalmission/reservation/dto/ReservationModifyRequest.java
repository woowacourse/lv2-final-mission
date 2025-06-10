package finalmission.reservation.dto;

import java.time.LocalDateTime;

public record ReservationModifyRequest(Long reservationId, LocalDateTime time, String description, Long roomId) {
}
