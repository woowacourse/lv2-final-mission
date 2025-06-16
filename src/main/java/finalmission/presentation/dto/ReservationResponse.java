package finalmission.presentation.dto;

import finalmission.domain.Reservation;
import finalmission.domain.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
    String room,
    LocalDate date,
    LocalTime time,
    String crew,
    String description,
    String status
) {

    public static ReservationResponse of(
        final Schedule schedule,
        final Reservation reservation
    ) {
        return new ReservationResponse(schedule.room().getTitle(), schedule.date(), schedule.time(),
            reservation.getDetails().crew(), reservation.getDetails().description(),
            reservation.getStatus().getTitle());
    }
}
