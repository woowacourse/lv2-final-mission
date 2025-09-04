package finalmission.dto.response;

import finalmission.domain.Meeting;
import java.time.LocalDate;
import java.time.LocalTime;

public record AllMeetingResponse(
        String coachName,
        LocalDate meetingDate,
        LocalTime meetingTime
) {

    public static AllMeetingResponse from(Meeting meeting) {
        return new AllMeetingResponse(
                meeting.getCoach().getName(),
                meeting.getDateTime().toLocalDate(),
                meeting.getDateTime().toLocalTime()
        );
    }
}
