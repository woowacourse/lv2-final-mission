package finalmission.domain.service.dto;

import java.time.LocalDate;

public record ReservationLessonRequest(
        Long trainerId,
        Long lessonTimeId,
        LocalDate date
) {
}
