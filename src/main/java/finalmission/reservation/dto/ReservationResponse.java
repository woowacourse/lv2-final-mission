package finalmission.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.time.dto.ReservationTimeResponse;
import java.time.LocalDate;

public record ReservationResponse(
        long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        ReservationTimeResponse time
) {
}
