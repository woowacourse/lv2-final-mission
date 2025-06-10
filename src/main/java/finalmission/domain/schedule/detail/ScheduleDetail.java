package finalmission.domain.schedule.detail;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.util.Objects;

@Embeddable
public class ScheduleDetail {

    @Embedded
    private DateSchedule date;

    @Embedded
    private TimeSchedule time;

    @Embedded
    private MaximumCapacity maximumCapacity;

    protected ScheduleDetail() {
    }

    public DateSchedule getDate() {
        return date;
    }

    public TimeSchedule getTime() {
        return time;
    }

    public MaximumCapacity getMaximumCapacity() {
        return maximumCapacity;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final ScheduleDetail that)) {
            return false;
        }

        return Objects.equals(getDate(), that.getDate()) && Objects.equals(getTime(), that.getTime())
                && Objects.equals(getMaximumCapacity(), that.getMaximumCapacity());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getDate());
        result = 31 * result + Objects.hashCode(getTime());
        result = 31 * result + Objects.hashCode(getMaximumCapacity());
        return result;
    }
}
