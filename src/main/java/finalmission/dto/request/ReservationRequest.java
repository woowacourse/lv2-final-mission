package finalmission.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        Long toiletId,
        LocalDate date,
        LocalTime startAt,
        LocalTime endAt
) {
}
