package finalmission.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestRent(
        Long carId,
        LocalDate date,
        LocalTime startTime,
        LocalTime returnTime
) {
}
