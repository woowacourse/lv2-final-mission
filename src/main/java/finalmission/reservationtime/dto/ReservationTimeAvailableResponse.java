package finalmission.reservationtime.dto;

import java.time.LocalTime;

public record ReservationTimeAvailableResponse(
        Long id,
        LocalTime startAt,
        boolean isBooked
) {
}
