package finalmission.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeResponse(
        LocalDate date,
        LocalTime time
) {
}
