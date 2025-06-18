package finalmission.controller.dto;

import finalmission.domain.Member;
import finalmission.domain.Reservation;
import finalmission.domain.Room;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        Long roomId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime
) {

    public Reservation toReservation(Member member, Room room) {
        return new Reservation(
                member,
                room,
                date,
                startTime,
                endTime
        );
    }
}
