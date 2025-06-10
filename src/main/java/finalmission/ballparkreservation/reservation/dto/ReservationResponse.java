package finalmission.ballparkreservation.reservation.dto;

import finalmission.ballparkreservation.reservation.Reservation;
import finalmission.ballparkreservation.schedule.Schedule;

public record ReservationResponse(
        Schedule schedule,
        String memberName
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getSchedule(), reservation.getMember().getName());
    }
}
