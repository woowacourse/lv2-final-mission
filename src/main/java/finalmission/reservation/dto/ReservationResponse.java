package finalmission.reservation.dto;

import finalmission.member.dto.MemberResponse;
import finalmission.room.dto.RoomResponse;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        MemberResponse member,
        RoomResponse room,
        LocalDate date,
        LocalTime time
) {
}
