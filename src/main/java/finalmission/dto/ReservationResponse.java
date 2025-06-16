package finalmission.dto;

import finalmission.domain.reservation.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
    Long reservationId,
    String crewName,
    String coachName,
    LocalDate date,
    LocalTime reservationTime
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getCrew().getName(),
            reservation.getCoach().getName(),
            reservation.getDate(),
            reservation.getReservationTime().getStartAt()
        );
    }
}