package finalmission.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

public record RoomCreateRequest(
        LocalDate startDate,
        LocalDate endDate,
        LocalTime startTime,
        LocalTime endTime,
        boolean isAnonymousRoom
) {
}
