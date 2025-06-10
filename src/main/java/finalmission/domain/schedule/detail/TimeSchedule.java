package finalmission.domain.schedule.detail;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import java.util.Objects;

@Embeddable
public class TimeSchedule {

    private LocalTime time;

    public TimeSchedule(final LocalTime time) {
        this.time = time;
    }

    protected TimeSchedule() {

    }

    public LocalTime getValue() {
        return time;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final TimeSchedule that)) {
            return false;
        }

        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(time);
    }
}
