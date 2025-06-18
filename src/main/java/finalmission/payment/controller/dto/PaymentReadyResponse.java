package finalmission.payment.controller.dto;

public record PaymentReadyResponse(
        String tid,
        String nextRedirectUrl
) {
}
