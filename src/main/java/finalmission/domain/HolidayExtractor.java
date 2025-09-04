package finalmission.domain;

import java.util.List;

public interface HolidayExtractor {

    List<Holiday> extract(int year, int month);
}
