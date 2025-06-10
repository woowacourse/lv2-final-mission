package finalmission.reservation.dto;

import java.time.LocalDate;

public record ReservationRequest(
        LocalDate date,
        int subway_number,
        String seat,
        String departStation,
        String arriveStation
) {
}
