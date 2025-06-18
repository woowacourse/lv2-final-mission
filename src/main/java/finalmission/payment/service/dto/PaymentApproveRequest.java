package finalmission.payment.service.dto;

public record PaymentApproveRequest(
        String tid,
        String pgToken,
        Long reservationId
) {
}
