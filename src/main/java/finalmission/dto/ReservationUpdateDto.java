package finalmission.dto;

import finalmission.model.Member;
import finalmission.model.Reservation;
import finalmission.model.Seat;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationUpdateDto(
        Long seatId,
        LocalDate reservationDate,
        LocalTime startAt,
        LocalTime endAt
) {
    public Reservation toReservation(Seat seat, Member member) {
        return new Reservation(
                member,
                seat,
                reservationDate,
                startAt,
                endAt
        );
    }
}
