package finalmission.holiday.service;

import finalmission.holiday.HolidayClient;
import finalmission.holiday.KoreaAnniversaries;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class HolidayCheckService {

    private final HolidayClient holidayClient;

    public HolidayCheckService(HolidayClient holidayClient) {
        this.holidayClient = holidayClient;
    }

    public boolean isHoliday(LocalDate date) {
        String year = date.format(DateTimeFormatter.ofPattern("yyyy"));
        String month = date.format(DateTimeFormatter.ofPattern("MM"));
        KoreaAnniversaries koreaAnniversaries = holidayClient.requestKoreaAnniversaries(year, month);
        return koreaAnniversaries.isHoliday(date);
    }
}
