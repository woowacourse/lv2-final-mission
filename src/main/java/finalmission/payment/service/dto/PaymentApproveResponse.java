package finalmission.payment.service.dto;

public record PaymentApproveResponse(
        Long id,
        Long reservationId,
        String tid,
        Long amount
) {
}
