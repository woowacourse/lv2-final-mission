package finalmission.running.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
    Long id,
    String creator,
    LocalDate date,
    LocalTime startAt,
    LocalTime endTime
) {
}
