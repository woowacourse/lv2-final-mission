package finalmission.domain.schedule.detail;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class DateSchedule {

    private LocalDate date;

    public DateSchedule(final LocalDate date) {
        this.date = date;
    }

    protected DateSchedule() {

    }

    public LocalDate getValue() {
        return date;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final DateSchedule that)) {
            return false;
        }

        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(date);
    }
}
