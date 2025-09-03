package finalmission.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import finalmission.domain.ReservationTime;
import java.time.LocalTime;

public record ReservationTimeCreateRequest(
        @JsonFormat(pattern = "HH:mm") LocalTime time
) {
    public ReservationTime toDomain() {
        return ReservationTime.withoutId(time);
    }
}
