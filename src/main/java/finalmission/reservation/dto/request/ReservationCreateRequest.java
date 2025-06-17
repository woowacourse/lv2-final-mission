package finalmission.reservation.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(
        Long memberId,
        Long roomId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String purpose
) {
}
