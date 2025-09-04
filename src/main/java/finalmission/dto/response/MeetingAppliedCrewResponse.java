package finalmission.dto.response;

import finalmission.domain.EducationPart;
import finalmission.domain.Meeting;
import finalmission.domain.MeetingStatus;
import java.time.LocalDateTime;

public record MeetingAppliedCrewResponse(
        Long crewId,
        String crewName,
        EducationPart educationPart,
        LocalDateTime meetingDateTime,
        String content,
        MeetingStatus meetingStatus
) {

    public static MeetingAppliedCrewResponse from(Meeting meeting) {
        return new MeetingAppliedCrewResponse(
                meeting.getCrew().getId(),
                meeting.getCrew().getName(),
                meeting.getCrew().getEducationPart(),
                meeting.getDateTime(),
                meeting.getContent(),
                meeting.getStatus()
        );
    }
}
