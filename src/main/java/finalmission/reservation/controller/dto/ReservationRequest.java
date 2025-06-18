package finalmission.reservation.controller.dto;

public record ReservationRequest(
        Long concertId,
        Long seatId,
        String tid,
        String pgToken
) {
}
