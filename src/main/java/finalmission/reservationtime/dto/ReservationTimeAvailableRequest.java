package finalmission.reservationtime.dto;

import java.time.LocalDate;

public record ReservationTimeAvailableRequest(
        LocalDate date,
        Long roomId
) {
}
