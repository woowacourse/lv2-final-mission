package finalmission.reservation.dto;

import java.time.LocalDate;

public record ReservationUpdateRequest(
        LocalDate date,
        Long roomId,
        Long timeId
) {
}
