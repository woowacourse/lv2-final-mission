package finalmission.reservation.dto.request;

import java.time.LocalDate;

public record ReservationCreateRequest(long userId, LocalDate reservationDate) {
}
