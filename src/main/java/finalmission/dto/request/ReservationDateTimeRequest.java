package finalmission.dto.request;

import finalmission.domain.ReservationDateTime;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationDateTimeRequest(
        @NotNull
        LocalDate date,

        @NotNull
        LocalTime startAt
) {

    public ReservationDateTime toReservationDateTimeWithoutId() {
        return new ReservationDateTime(null, date, startAt);
    }
}
