package finalmission.reservation.dto;

import java.time.LocalDateTime;

public record ReservationCreateRequest(LocalDateTime time, String description, Long roomId) {
}
