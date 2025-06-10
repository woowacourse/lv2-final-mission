package finalmission.reservation.dto;

import java.time.LocalDate;
import finalmission.reservation.Seat;
import finalmission.station.domain.Station;

public record ReservationRequest(
        LocalDate date,
        int subway_number,
        String seat,
        String departStation,
        String arriveStation
) {
}
