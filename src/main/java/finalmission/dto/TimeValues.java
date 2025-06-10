package finalmission.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record TimeValues(
        LocalDate date,
        List<LocalTime> times
) {
}
