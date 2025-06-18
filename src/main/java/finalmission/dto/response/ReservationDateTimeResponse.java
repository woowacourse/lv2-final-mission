package finalmission.dto.response;

import finalmission.domain.ReservationDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDateTimeResponse(LocalDate date, LocalTime startAt) {

    public static ReservationDateTimeResponse from(ReservationDateTime reservationDateTime) {
        return new ReservationDateTimeResponse(reservationDateTime.getDate(), reservationDateTime.getStartAt());
    }
}
