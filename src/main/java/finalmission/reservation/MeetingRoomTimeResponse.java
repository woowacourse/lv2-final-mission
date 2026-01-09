package finalmission.reservation;

import java.time.LocalTime;

public record MeetingRoomTimeResponse(
    Long timeId,
    LocalTime time,
    boolean isReserved
) {

}
