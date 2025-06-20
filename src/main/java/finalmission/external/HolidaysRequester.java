package finalmission.external;

import finalmission.dto.HolidayResponse;
import java.time.LocalDate;
import java.util.List;

public interface HolidaysRequester {

    List<HolidayResponse> getHolidays(final LocalDate date);

}
