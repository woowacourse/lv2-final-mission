package finalmission.dto.reservation;

import finalmission.domain.reservation.Reservation;
import finalmission.dto.schedule.ScheduleResponse;

public record CreateReservationResponse(
        long id,
        OwnerResponse owner,
        ReservationDetailResponse detail,
        ScheduleResponse schedule
) {
    
    public CreateReservationResponse(final Reservation reservation, final int remainingCapacity) {
        this(reservation.getId(),
                new OwnerResponse(reservation.getOwner()),
                new ReservationDetailResponse(reservation.getDetail()),
                new ScheduleResponse(reservation.getSchedule(), remainingCapacity));
    }
}
