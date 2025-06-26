package finalmission.holiday.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.holiday.database.HolidayRepository;
import finalmission.holiday.model.Holiday;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;

@Service
public class NationalHolidayService {

    private final HolidayRepository holidayRepository;
    private final NationalHolidayRestClient nationalHolidayRestClient;

    public NationalHolidayService(HolidayRepository holidayRepository, NationalHolidayRestClient nationalHolidayRestClient) {
        this.holidayRepository = holidayRepository;
        this.nationalHolidayRestClient = nationalHolidayRestClient;
    }

    public void createNationalHolidaysOfThisYear() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        int thisYear = Year.now().getValue();
        for (int month=1; month<=12; month++) {
            String holidayResponses = nationalHolidayRestClient.getHolidays(thisYear, month);
            saveHolidaysToDB(holidayResponses, dateTimeFormatter);
        }
    }

    private void saveHolidaysToDB(String holidayResponses, DateTimeFormatter dateTimeFormatter) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(holidayResponses);
            JsonNode holidays = root
                    .path("response")
                    .path("body")
                    .path("items")
                    .path("item");
            if (holidays.isArray()) {
                for (JsonNode holiday : holidays) {
                    String name = holiday.path("dateName").asText();
                    System.out.println("name:   " + name);
                    LocalDate date = LocalDate.parse(holiday.path("locdate").asText(), dateTimeFormatter);
                    holidayRepository.save(new Holiday(date, name));
                }
            }
        } catch (JsonProcessingException exception) {
            throw new IllegalArgumentException("응답 파싱 도중 예외가 발생하였습니다.");
        }
    }
}
