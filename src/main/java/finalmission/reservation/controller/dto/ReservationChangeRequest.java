package finalmission.reservation.controller.dto;

public record ReservationChangeRequest(
        Long reservationId,
        Integer seatNumber
) {
}
