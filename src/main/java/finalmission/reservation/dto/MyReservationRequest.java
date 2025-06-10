package finalmission.reservation.dto;

import java.time.LocalDate;

public record MyReservationRequest(
        String name,
        LocalDate date,
        Long timeId,
        Long exerciseCourseId
) {
    public boolean isEmpty() {
        if (name == null || date == null || timeId == null || exerciseCourseId == null) {
            return true;
        }
        return false;
    }
}
