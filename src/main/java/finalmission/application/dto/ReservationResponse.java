package finalmission.application.dto;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.YogaClass;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ReservationResponse(
        Member member,
        YogaClass yogaClass,
        LocalDate date,
        LocalTime time
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getMember(), reservation.getYogaClass(), reservation.getDate(), reservation.getTime());
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream().map(ReservationResponse::from).toList();
    }
}
