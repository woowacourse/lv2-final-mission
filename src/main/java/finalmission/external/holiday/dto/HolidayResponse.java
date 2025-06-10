package finalmission.external.holiday.dto;

import java.time.LocalDate;

public record HolidayResponse(
        String dateName,
        LocalDate date
) {
}
