package finalmission.reservationTime.controller.dto.response;

import finalmission.reservationTime.domain.ReservationTime;

public record ReservationTimeResponse(long id, String bookedAt) {

    public static ReservationTimeResponse from(final ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getBookedAt());
    }
}
