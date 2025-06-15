package finalmission.planning.ui.dto.response;

import finalmission.planning.domain.Reservation;

public record ReservationResponse (
        Long id,
        String ownerName,
        ReservationSlotResponse reservationSlot
){
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getUser().getName(),
                ReservationSlotResponse.from(reservation.getReservationSlot())
        );
    }
}
