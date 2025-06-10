package finalmission.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ReservationCreateRequest(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate reservationDate,
        long umbrellaId,
        long memberId,
        String paymentKey,
        String orderId,
        Long amount) {
}
