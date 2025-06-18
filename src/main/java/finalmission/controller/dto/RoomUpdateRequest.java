package finalmission.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RoomUpdateRequest(
        String name,
        LocalDate startDate,
        LocalTime startTime,
        String description
) {
}
