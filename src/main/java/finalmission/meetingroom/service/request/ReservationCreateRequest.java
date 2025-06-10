package finalmission.meetingroom.service.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(
        String meetingRoomName,
        LocalDate reservationDate,
        LocalTime startAt,
        LocalTime endAt
) {
}
