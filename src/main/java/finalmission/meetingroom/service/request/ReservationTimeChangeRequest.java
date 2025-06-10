package finalmission.meetingroom.service.request;

import java.time.LocalTime;

public record ReservationTimeChangeRequest(
        LocalTime newStartAt,
        LocalTime newEndAt
) {
}
