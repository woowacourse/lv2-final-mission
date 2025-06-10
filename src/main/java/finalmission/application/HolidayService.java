package finalmission.application;

import finalmission.infrastructure.HolidayClient;
import finalmission.infrastructure.Holiday;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    private final HolidayClient holidayClient;

    public HolidayService(final HolidayClient holidayClient) {
        this.holidayClient = holidayClient;
    }

    public boolean isHoliday(LocalDate date) {
        List<Holiday> holidays = holidayClient.getHoliday(date.getYear(), date.getMonthValue());
        return holidays.stream().anyMatch(holiday -> holiday.getDate().equals(date));
    }
}
