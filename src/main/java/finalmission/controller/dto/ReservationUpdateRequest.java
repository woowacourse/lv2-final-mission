package finalmission.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationUpdateRequest(
        Long roomId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {
}
