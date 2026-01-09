package finalmission.reservation;

import java.time.LocalDate;

public record ReservationRequest(
    Long meetingRoomId,
    Long timeId,
    LocalDate date
) {

}
