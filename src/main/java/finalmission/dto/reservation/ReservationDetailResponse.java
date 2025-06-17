package finalmission.dto.reservation;

import finalmission.domain.reservation.detail.ReservationDetail;

public record ReservationDetailResponse(
        int numberOfGuest,
        String reservationMessage
) {

    public ReservationDetailResponse(final ReservationDetail detail) {
        this(
                detail.getNumberOfGuest().getValue(),
                detail.getReservatonMessage().getMessage()
        );
    }
}
