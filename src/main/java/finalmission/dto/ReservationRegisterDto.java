package finalmission.dto;

import finalmission.model.Member;
import finalmission.model.Reservation;
import finalmission.model.ReservationSchedule;
import finalmission.model.Seat;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRegisterDto(
        Long seatId,
        LocalDate reservationDate,
        LocalTime startAt,
        LocalTime endAt
) {
    public Reservation toReservation(Member member, Seat seat) {
        return new Reservation(member, seat, new ReservationSchedule(reservationDate, startAt, endAt));
    }
}
