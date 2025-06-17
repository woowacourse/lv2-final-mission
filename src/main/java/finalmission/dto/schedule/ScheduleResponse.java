package finalmission.dto.schedule;

import finalmission.domain.schedule.Schedule;
import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleResponse(
        long id,
        LocalDate date,
        LocalTime time,
        int availableNumberOfGuest
) {

    public ScheduleResponse(final Schedule schedule, final int remainingCapacity) {
        this(
                schedule.getId(),
                schedule.getDate().getValue(),
                schedule.getTime().getValue(),
                remainingCapacity
        );
    }
}
