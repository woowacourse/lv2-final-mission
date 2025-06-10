package finalmission.domain.repository;

import finalmission.domain.Holiday;
import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository {

    List<Holiday> findByLocalDate(LocalDate localDate);
}
