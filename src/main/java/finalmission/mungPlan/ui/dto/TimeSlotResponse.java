package finalmission.mungPlan.ui.dto;

import finalmission.mungPlan.domain.TimeSlot;
import java.time.LocalTime;

public record TimeSlotResponse(
        LocalTime startAt,
        LocalTime endAt
) {
    public TimeSlotResponse(TimeSlot timeSlot) {
        this(timeSlot.getStartAt(), timeSlot.getEndAt());
    }
}
