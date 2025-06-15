package finalmission.planning.ui.dto.response;

import finalmission.planning.domain.ReservationSlot;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationSlotResponse(
        Long id,
        LocalDate planDate,
        LocalTime startTime,
        LocalTime endTime
) {
    public static ReservationSlotResponse from(ReservationSlot reservationSlot) {
        return new ReservationSlotResponse(
                reservationSlot.getId(),
                reservationSlot.getPlanDate().getDate(),
                reservationSlot.getTimeSlot().getStartTime(),
                reservationSlot.getTimeSlot().getEndTime());
    }
}
