package finalmission.presentation.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequest(
        LocalDate date,
        LocalTime time,
        Long designId,
        Long designerId
) {
}
