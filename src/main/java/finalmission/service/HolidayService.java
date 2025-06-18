package finalmission.service;

import finalmission.domain.HolidayClient;
import finalmission.dto.response.HolidayElementResponse;
import finalmission.exception.custom.InvalidValueException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    private final HolidayClient holidayClient;

    public HolidayService(final HolidayClient holidayClient) {
        this.holidayClient = holidayClient;
    }

    public void checkHoliday(LocalDate date) {
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new InvalidValueException("주말에는 예약할 수 없습니다.");
        }

        List<HolidayElementResponse> holidayData = holidayClient.getHolidayData(date);

        if (holidayData.isEmpty()) {
            return;
        }

        for (HolidayElementResponse holidayElementResponse : holidayData) {
            isHoliday(date, holidayElementResponse);
        }
    }

    private static void isHoliday(final LocalDate date, final HolidayElementResponse holidayElementResponse) {
        if (holidayElementResponse.isHoliday().equals("Y") && holidayElementResponse.locdate().equals(date)) {
            throw new InvalidValueException("공휴일에는 예약할 수 없습니다.: %s".formatted(holidayElementResponse.dateName()));
        }
    }
}
