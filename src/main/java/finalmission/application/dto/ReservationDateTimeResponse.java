package finalmission.application.dto;

import finalmission.domain.ReservationDateTime;
import java.time.LocalDateTime;

public class ReservationDateTimeResponse {
    private LocalDateTime dateTime;

    private ReservationDateTimeResponse() {
    }

    public ReservationDateTimeResponse(final ReservationDateTime reservationDateTime) {
        this.dateTime = reservationDateTime.getDateTime();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
