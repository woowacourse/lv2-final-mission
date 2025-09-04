package finalmission.dto.response;

import finalmission.domain.EducationPart;
import finalmission.domain.Meeting;
import finalmission.domain.MeetingStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record MeetingResponse(
        String coachName,
        EducationPart coachEducationPart,
        LocalDate meetingDate,
        LocalTime meetingTime,
        String content,
        MeetingStatus meetingStatus
) {

    public static MeetingResponse from(Meeting meeting) {
        return new MeetingResponse(
                meeting.getCoach().getName(),
                meeting.getCoach().getEducationPart(),
                meeting.getDateTime().toLocalDate(),
                meeting.getDateTime().toLocalTime(),
                meeting.getContent(),
                meeting.getStatus()
        );
    }
}
