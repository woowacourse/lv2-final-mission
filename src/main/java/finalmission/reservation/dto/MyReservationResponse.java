package finalmission.reservation.dto;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record MyReservationResponse(
        Long id,
        String name,
        LocalDate date,
        LocalTime time,
        String className
) {
    public static MyReservationResponse from(Reservation reservation) {
        return new MyReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getStartAt(),
                reservation.getExerciseCourse().getName()
        );
    }
}
