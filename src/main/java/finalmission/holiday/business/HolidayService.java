package finalmission.holiday.business;

import finalmission.holiday.database.HolidayRepository;
import finalmission.holiday.model.Holiday;
import finalmission.holiday.presentation.dto.request.HolidayCreateWebRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final NationalHolidayService nationalHolidayService;

    public HolidayService(HolidayRepository holidayRepository, NationalHolidayService nationalHolidayService) {
        this.holidayRepository = holidayRepository;
        this.nationalHolidayService = nationalHolidayService;
    }

    public Holiday create(HolidayCreateWebRequest holidayCreateWebRequest) {
        return holidayRepository.save(new Holiday(holidayCreateWebRequest.date(), holidayCreateWebRequest.name()));
    }

    public void createNationalHolidaysOfThisYear() {
        nationalHolidayService.createNationalHolidaysOfThisYear();
    }

    public boolean existsByDate(LocalDate date) {
        return holidayRepository.existsByDate(date);
    }
}
