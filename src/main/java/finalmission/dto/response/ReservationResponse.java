package finalmission.dto.response;

import finalmission.domain.Reservation;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(LocalDate date, LocalTime startAt, String userName, int guest, int price) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getDateTime().getDate(),
                reservation.getDateTime().getStartAt(),
                reservation.getMember().getName(),
                reservation.getGuest().getSize(),
                reservation.getPrice().getPrice()
        );
    }
}
