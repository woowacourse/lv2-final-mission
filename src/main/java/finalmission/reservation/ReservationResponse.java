package finalmission.reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
    Long reservationId,
    String meetingRoomName,
    LocalTime startTime,
    LocalTime endTime,
    LocalDate date
) {

}
