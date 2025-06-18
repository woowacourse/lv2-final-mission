package finalmission.reservation.controller.dto;

public record ReservationResponse(
        Long id,
        Long memberId,
        Long concertId,
        Long seatId
) {
}
