package finalmission.presentation.dto;

public record PaymentInfo(String paymentKey, String orderId, Long amount) {
    public static PaymentInfo of(String paymentKey, String orderId, Long amount) {
        return new PaymentInfo(paymentKey, orderId, amount);
    }
}
