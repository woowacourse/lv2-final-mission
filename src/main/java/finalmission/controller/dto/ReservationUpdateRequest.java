package finalmission.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationUpdateRequest(
        LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime time
) {
}
