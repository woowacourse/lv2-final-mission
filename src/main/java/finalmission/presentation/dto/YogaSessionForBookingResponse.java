package finalmission.presentation.dto;

import finalmission.domain.YogaSessionForBooking;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record YogaSessionForBookingResponse(
        long sessionId,
        long courseId,
        String courseName,
        String instructor,
        LocalDate date,
        LocalTime time,
        int maximumAttendance,
        long currentAttendance,
        boolean isFullBooking
) {

    public static YogaSessionForBookingResponse from(YogaSessionForBooking sessionForBooking) {
        return new YogaSessionForBookingResponse(
                sessionForBooking.session().getId(),
                sessionForBooking.session().getCourse().getId(),
                sessionForBooking.session().getCourse().getName(),
                sessionForBooking.session().getCourse().getInstructor(),
                sessionForBooking.session().getDate(),
                sessionForBooking.session().getTime(),
                sessionForBooking.session().getMaximumAttendance(),
                sessionForBooking.currentAttendance(),
                sessionForBooking.isFullBooking()
        );
    }

    public static List<YogaSessionForBookingResponse> from(List<YogaSessionForBooking> sessionsForBooking) {
        return sessionsForBooking.stream()
                .map(YogaSessionForBookingResponse::from)
                .toList();
    }
}
