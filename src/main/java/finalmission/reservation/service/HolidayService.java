package finalmission.reservation.service;

import finalmission.client.HolidayClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HolidayService {

    private final HolidayClient holidayClient;

    public HolidayService(final HolidayClient holidayClient) {
        this.holidayClient = holidayClient;
    }

    @Transactional(propagation = Propagation.NEVER)
    public void validateHoliday(LocalDateTime reservationDateTime) {
        String monthValue = getMonthValue(reservationDateTime);
        List<LocalDate> holidays = holidayClient.getHoliday(reservationDateTime.getYear(), monthValue);

        if (!holidays.isEmpty() && holidays.contains(reservationDateTime.toLocalDate())) {
            throw new IllegalArgumentException("해당 날짜는 공휴일입니다.");
        }
    }

    private String getMonthValue(final LocalDateTime reservationDateTime) {
        String monthValue = String.valueOf(reservationDateTime.getMonth().getValue());
        if (monthValue.length() == 1) {
            monthValue = "0" + monthValue;
        }
        return monthValue;
    }
}
