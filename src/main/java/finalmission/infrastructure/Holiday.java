package finalmission.infrastructure;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Holiday(String dateKind, String dateName, String isHoliday, String locdate, int seq) {

    public LocalDate getDate() {
        return LocalDate.parse(locdate, DateTimeFormatter.BASIC_ISO_DATE);
    }
}
