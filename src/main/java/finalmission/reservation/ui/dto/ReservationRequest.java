package finalmission.reservation.ui.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        LocalDate date,
        LocalTime time,
        Long coachId,
        Long crewId
) {

}
