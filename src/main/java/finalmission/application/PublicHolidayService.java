package finalmission.application;

import finalmission.infrastructure.PublicHolidayHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class PublicHolidayService {

    private final PublicHolidayHandler publicHolidayHandler;

    public PublicHolidayService(PublicHolidayHandler publicHolidayHandler) {
        this.publicHolidayHandler = publicHolidayHandler;
    }

    public void validatePublicHoliday(final LocalDate date) {
        boolean isPublicHoliday = publicHolidayHandler.isPublicHoliday(date);

        if (isPublicHoliday) {
            throw new IllegalArgumentException("해당 요일은 공휴일로 영업하지 않습니다.");
        }
    }
}
