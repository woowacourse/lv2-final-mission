package finalmission.mungPlan.ui.dto;

import finalmission.mungPlan.domain.Reservation;
import java.time.LocalDate;

public record ReservationResponse(
        UserResponse user,
        LocalDate date,
        TimeSlotResponse time
) {
    public ReservationResponse(Reservation reservation) {
        this(
                new UserResponse(reservation.getUser()),
                reservation.getPlanDate().getDate(),
                new TimeSlotResponse(reservation.getTimeSlot())
        );
    }
}
