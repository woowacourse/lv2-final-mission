package finalmission.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record MyReservationRequest(
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
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
