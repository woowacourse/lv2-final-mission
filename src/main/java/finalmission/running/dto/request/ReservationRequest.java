package finalmission.running.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationRequest(
    String reg,
    List<String> participants,
    LocalDate date,
    LocalTime startAt,
    LocalTime endTime
) {
}
