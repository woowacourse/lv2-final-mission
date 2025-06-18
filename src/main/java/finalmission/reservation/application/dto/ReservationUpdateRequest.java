package finalmission.reservation.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationUpdateRequest(
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startAt,
        @JsonFormat(pattern = "HH:mm")
        LocalTime endAt
) {
}
