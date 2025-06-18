package finalmission.controller.dto;

import finalmission.domain.Room;
import java.time.LocalDate;
import java.time.LocalTime;

public record RoomUpdateResponse(
        Long roomId,
        String name,
        LocalDate startDate,
        LocalTime startTime,
        String description,
        Long memberId
) {

    public static RoomUpdateResponse from(final Room room) {
        return new RoomUpdateResponse(
                room.getId(),
                room.getName(),
                room.getStartDate(),
                room.getStartTime(),
                room.getDescription(),
                room.getManager().getId()
        );
    }
}
