package finalmission.reservation.dto.request;

import java.time.LocalDate;

public record ReservationRequest(long userId, LocalDate reservationDate) {
}
