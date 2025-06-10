package finalmission.reservationtime.dto;

import java.time.LocalTime;

public record ReservationTimeResponse(
        Long id,
        LocalTime startAt
) {
}
