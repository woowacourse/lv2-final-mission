package finalmission.reservation.dto;

import java.time.LocalDate;

public record ReservationSearchRequest(LocalDate date, Long roomId) {
}
