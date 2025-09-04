package finalmission.dto.request;

import finalmission.domain.Coach;
import finalmission.domain.Crew;
import finalmission.domain.Meeting;
import finalmission.domain.MeetingStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record CreateMeetingRequest(
        Long coachId,
        LocalDate date,
        LocalTime time,
        String content
) {

    public Meeting toMeeting(MeetingStatus meetingStatus, Coach coach, Crew crew) {
        return Meeting.builder()
                .dateTime(LocalDateTime.of(date, time))
                .content(content)
                .coach(coach)
                .crew(crew)
                .status(meetingStatus)
                .build();
    }
}
