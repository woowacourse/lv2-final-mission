package finalmission.presentation.dto;

public record ReservationRequest(ReservationSpec reservationSpec, PaymentInfo paymentInfo) {
    public static ReservationRequest of(ReservationSpec reservationSpec, PaymentInfo paymentInfo) {
        return new ReservationRequest(reservationSpec, paymentInfo);
    }
}
