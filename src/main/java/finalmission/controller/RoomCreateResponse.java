package finalmission.controller;

import finalmission.domain.Room;
import java.time.LocalDate;
import java.time.LocalTime;

public record RoomCreateResponse(
        Long roomId,
        String name,
        LocalDate startDate,
        LocalTime startTime,
        String description,
        Long memberId
) {

    public static RoomCreateResponse from(final Room room) {
        return new RoomCreateResponse(
                room.getId(),
                room.getName(),
                room.getStartDate(),
                room.getStartTime(),
                room.getDescription(),
                room.getManager().getId()
        );
    }
}
