package finalmission.presentation.dto;

public record PaymentApproveResponseDto(String paymentKey, String orderId, Long totalAmount) {
}
