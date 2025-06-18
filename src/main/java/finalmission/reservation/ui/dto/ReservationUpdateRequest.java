package finalmission.reservation.ui.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationUpdateRequest(
        Long id,
        LocalDate date,
        LocalTime time,
        Long memberId
) {

}
