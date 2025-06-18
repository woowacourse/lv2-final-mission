package finalmission.payment.controller.dto;

public record PaymentReadyRequest(
        Long concertId,
        Long seatId
) {
}
