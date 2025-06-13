package finalmission.reservation.service;

import finalmission.client.HolidayClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HolidayService {

    private final HolidayClient holidayClient;

    public HolidayService(final HolidayClient holidayClient) {
        this.holidayClient = holidayClient;
    }

    public void validateHoliday(LocalDateTime reservationDateTime) {
        String monthValue = String.valueOf(reservationDateTime.getMonth().getValue());
        if (monthValue.length() == 1) {
            monthValue = "0" + monthValue;
        }
        List<LocalDate> holidays = holidayClient.getHoliday(reservationDateTime.getYear(), monthValue);

        if (!holidays.isEmpty() && holidays.contains(reservationDateTime.toLocalDate())) {
            throw new IllegalArgumentException("해당 날짜는 공휴일입니다.");
        }
    }
}
