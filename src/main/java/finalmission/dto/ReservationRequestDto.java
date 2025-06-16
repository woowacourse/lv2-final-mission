package finalmission.dto;

import java.time.LocalDate;

public record ReservationRequestDto(
    Long coachId,
    Long reservationTimeId,
    LocalDate date
) {
}
