package finalmission.controller.dto;

import finalmission.domain.Room;
import finalmission.domain.vo.LolName;
import java.time.LocalDate;
import java.time.LocalTime;

public record RoomResponse(
        String name,
        LocalDate startDate,
        LocalTime startTime,
        String description,
        LolName manager
) {

    public static RoomResponse from(final Room room) {
        return new RoomResponse(
                room.getName(),
                room.getStartDate(),
                room.getStartTime(),
                room.getDescription(),
                room.getManager().getLolName()
        );
    }
}
