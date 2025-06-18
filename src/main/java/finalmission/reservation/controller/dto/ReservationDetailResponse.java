package finalmission.reservation.controller.dto;

import java.time.LocalDateTime;

public record ReservationDetailResponse(
        Long id,
        Long concertId,
        String concertName,
        LocalDateTime concertDate,
        Long seatId,
        Integer seatNumber,
        String tid,
        Long price
) {
}
