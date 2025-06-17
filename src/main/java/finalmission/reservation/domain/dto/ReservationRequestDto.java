package finalmission.reservation.domain.dto;

import finalmission.reservation.Reservation;
import finalmission.room.domain.Room;
import finalmission.user.User;

public record ReservationRequestDto(String content, Long roomId) {

    public Reservation toEntity(Room room, User user) {
        return new Reservation(content, room, user);
    }
}
