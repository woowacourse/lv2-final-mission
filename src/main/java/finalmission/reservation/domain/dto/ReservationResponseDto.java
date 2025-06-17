package finalmission.reservation.domain.dto;


import finalmission.reservation.Reservation;
import finalmission.room.domain.Room;
import finalmission.room.domain.dto.RoomResponseDto;
import finalmission.user.User;
import finalmission.user.domain.dto.UserResponseDto;

public record ReservationResponseDto(Long id, String content, RoomResponseDto roomResponseDto, UserResponseDto userResponseDto) {

    public static ReservationResponseDto of(Reservation reservation, Room room, User user) {
        RoomResponseDto roomResponseDto1 = RoomResponseDto.from(room);
        UserResponseDto userResponseDto1 = UserResponseDto.from(user);
        return new ReservationResponseDto(reservation.getId(), reservation.getContent(), roomResponseDto1,
                userResponseDto1);
    }
}
