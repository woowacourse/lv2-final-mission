package finalmission.dto.response;

import finalmission.entity.ReservationTime;
import java.time.LocalTime;

public record CreateReservationTimeResponse(Long id, LocalTime startAt) {

    public static CreateReservationTimeResponse from(ReservationTime reservationTime) {
        return new CreateReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }
}
