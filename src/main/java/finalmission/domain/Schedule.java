package finalmission.domain;

import java.time.LocalDate;
import java.time.LocalTime;

public record Schedule(
    Room room,
    LocalDate date,
    LocalTime time
) {

    public boolean isAvailableSchedule(final Schedule targetSchedule) {
        return !((targetSchedule.room().equals(this.room())) &&
            (targetSchedule.date().equals(this.date())) &&
            !(targetSchedule.time().plusMinutes(59).isBefore(this.time())) &&
            !(targetSchedule.time().isAfter(this.time().plusMinutes(59))));
    }
}
