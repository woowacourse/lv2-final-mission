package finalmission.reservation.dto;

import finalmission.member.dto.MemberResponse;
import finalmission.room.dto.RoomResponse;
import java.time.LocalDate;
import java.time.LocalTime;

public record MyReservationResponse(
        RoomResponse room,
        LocalDate date,
        LocalTime time,
        String description,
        MemberResponse member
) {
}
