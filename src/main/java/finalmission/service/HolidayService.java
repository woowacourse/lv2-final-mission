package finalmission.service;

import com.fasterxml.jackson.databind.JsonNode;
import finalmission.util.HolidayClient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    HolidayClient holidayClient;

    public HolidayService(HolidayClient holidayClient) {
        this.holidayClient = holidayClient;
    }

    public boolean isHolyDay(LocalDate localDate) {
        JsonNode root = holidayClient.getHolidays(localDate);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        JsonNode items = root
                .path("response")
                .path("body")
                .path("items")
                .path("item");

        for (JsonNode item : items) {
            int raw = item.get("locdate").asInt(); // e.g., 20250505
            LocalDate holyday = LocalDate.parse(String.valueOf(raw), dateTimeFormatter);
            if (holyday.equals(localDate)) {
                return true;
            }
        }

        return false;
    }
}
