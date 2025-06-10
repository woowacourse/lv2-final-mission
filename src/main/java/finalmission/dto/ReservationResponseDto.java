package finalmission.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDto(
        Long id,
        String roomName,
        int seatNumber,
        LocalDate reservationDate,
        LocalTime startAt,
        LocalTime endAt
) {
}
