package finalmission.presentation.dto;

import finalmission.domain.YogaSession;
import java.time.LocalDate;
import java.time.LocalTime;

public record YogaSessionResponse(
        long sessionId,
        long courseId,
        String courseName,
        String instructor,
        LocalDate date,
        LocalTime time,
        int maximumAttendance
) {

    public static YogaSessionResponse from(YogaSession session) {
        return new YogaSessionResponse(
                session.getId(),
                session.getCourse().getId(),
                session.getCourse().getName(),
                session.getCourse().getInstructor(),
                session.getDate(),
                session.getTime(),
                session.getMaximumAttendance()
        );
    }
}
