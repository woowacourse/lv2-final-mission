package finalmission.domain.repository;

import finalmission.client.HolidayClient;
import finalmission.domain.Holiday;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryHolidayRepository implements HolidayRepository {

    private final HolidayClient holidayClient;
    private final Map<LocalDate, List<Holiday>> holidays;

    public MemoryHolidayRepository(HolidayClient holidayClient) {
        this.holidayClient = holidayClient;
        this.holidays = new HashMap<>();
    }

    @Override
    public List<Holiday> findByLocalDate(LocalDate localDate) {
        if (holidays.containsKey(localDate)) {
            return holidays.get(localDate);
        }
        holidayClient.getHolidays(localDate)
                .getOrDefault(localDate, List.of())
                .stream()
                .map(holidayItem -> new Holiday(
                        LocalDate.parse(holidayItem.locdate()),
                        holidayItem.dateName(),
                        holidayItem.dateKind())
                )
                .toList()
                .forEach(holiday -> holidays.computeIfAbsent(holiday.getDate(), k -> new ArrayList<>()).add(holiday));
        return holidays.getOrDefault(localDate, List.of());
    }
}
