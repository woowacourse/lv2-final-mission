package finalmission.dto.response;

import finalmission.domain.Coach;
import finalmission.domain.EducationPart;
import finalmission.domain.Holiday;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record CoachResponse(
        String coachName,
        EducationPart educationPart,
        LocalTime startTime,
        LocalTime endTime,
        List<LocalDateTime> reservedMeetingTime,
        List<ImpossibleDate> impossibleDates
) {

    public record ImpossibleDate(
            String reason,
            LocalDate date
    ) {
    }

    public static CoachResponse of(Coach coach, List<LocalDateTime> reservedMeetingTime, List<Holiday> holidays) {
        List<ImpossibleDate> impossibleDates = holidays.stream()
                .map(holiday -> new ImpossibleDate(holiday.name(), holiday.date()))
                .toList();

        return new CoachResponse(
                coach.getName(),
                coach.getEducationPart(),
                coach.getStartTime(),
                coach.getEndTime(),
                reservedMeetingTime,
                impossibleDates
        );
    }
}
