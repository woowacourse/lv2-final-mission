package finalmission.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDetailResponseDto(
        Long id,
        String roomName,
        int seatNumber,
        LocalDateTime registeredAt,
        LocalDate reservationDate,
        LocalTime startAt,
        LocalTime endAt
) {
}
