package finalmission.api.v1.restaurant.dto;

import finalmission.api.v1.reservation.domain.ReservationTime;
import java.time.LocalTime;

public record ReservationTimeResponse(Long id, LocalTime time) {
    public ReservationTimeResponse(final ReservationTime reservationTime) {
        this(reservationTime.getId(),reservationTime.getTime());
    }
}
