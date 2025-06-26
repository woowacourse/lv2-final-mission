package finalmission.holiday.presentation.dto.request;

import java.time.LocalDate;

public record HolidayCreateWebRequest(LocalDate date, String name) {

    public HolidayCreateWebRequest {
        if (date == null) {
            throw new IllegalArgumentException("날짜는 null이 될 수 없습니다.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 빈 값이 될 수 없습니다.");
        }
    }
}
