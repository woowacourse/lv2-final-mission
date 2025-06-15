package finalmission.reservation.service.dto.response;

import finalmission.reservation.domain.Reservation;

public record ReservationResponse(
        Long id,
        ReservationInformationResponse reservationInformationResponse
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                ReservationInformationResponse.from(reservation.getReservationInformation())
        );
    }
}
