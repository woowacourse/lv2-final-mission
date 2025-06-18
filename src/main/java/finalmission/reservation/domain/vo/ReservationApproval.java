package finalmission.reservation.domain.vo;

import finalmission.reservation.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationApproval(LocalDate date,
                                  LocalTime time,
                                  String crew,
                                  String email
) {

    public static ReservationApproval of(Reservation reservation) {
        return new ReservationApproval(
                reservation.getDate(),
                reservation.getTime(),
                reservation.getCrew().getName(),
                reservation.getCrew().getEmail()
        );
    }
}
