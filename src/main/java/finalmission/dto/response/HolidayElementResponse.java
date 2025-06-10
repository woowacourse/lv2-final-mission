package finalmission.dto.response;

import java.time.LocalDate;

public record HolidayElementResponse(
        String dateName,
        String isHoliday,
        LocalDate locdate
) {
}
