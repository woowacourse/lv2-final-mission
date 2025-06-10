package finalmission.reservation.dto;

import finalmission.room.dto.RoomResponse;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        RoomResponse room,
        LocalDate date,
        LocalTime time
) {
}
