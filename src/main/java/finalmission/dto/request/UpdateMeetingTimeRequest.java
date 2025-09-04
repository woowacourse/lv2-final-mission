package finalmission.dto.request;

import java.time.LocalTime;

public record UpdateMeetingTimeRequest(
        LocalTime startTime,
        LocalTime endTime
) {

}
