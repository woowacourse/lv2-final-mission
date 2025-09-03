package finalmission.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatUtil {

    public static LocalDate toLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(date, formatter);
    }
}
