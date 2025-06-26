package finalmission.holiday.presentation;

import finalmission.holiday.business.HolidayService;
import finalmission.holiday.model.Holiday;
import finalmission.holiday.presentation.dto.request.HolidayCreateWebRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/holidays")
@RestController
public class HolidayController {

    private HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @PostMapping
    public ResponseEntity<Holiday> create(@RequestBody HolidayCreateWebRequest requestBody) {
        Holiday holiday = holidayService.create(requestBody);
        return ResponseEntity.status(HttpStatus.CREATED).body(holiday);
    }

    @PostMapping("/national")
    public void createNationalHolidaysOfThisYear() {
        holidayService.createNationalHolidaysOfThisYear();
    }
}
