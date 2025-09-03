package finalmission.dto.response;

import finalmission.domain.ReservationTime;
import java.time.LocalTime;

public record ReservationTimeResponse(
        Long id,
        LocalTime time
) {
    public static ReservationTimeResponse from(final ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getTime());
    }
}
