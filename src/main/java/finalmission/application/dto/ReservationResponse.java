package finalmission.application.dto;

import finalmission.domain.Reservation;
import finalmission.domain.User;
import finalmission.domain.YogaClass;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(
        User user,
        YogaClass yogaClass,
        LocalDate date,
        LocalTime time
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getUser(), reservation.getYogaClass(), reservation.getDate(),
                reservation.getTime());
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::from).toList();
    }
}
