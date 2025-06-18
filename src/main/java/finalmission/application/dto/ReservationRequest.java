package finalmission.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(long classId, LocalDate date, LocalTime time) {
}
