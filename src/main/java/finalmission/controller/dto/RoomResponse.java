package finalmission.controller.dto;

import finalmission.domain.Room;
import finalmission.domain.vo.LolName;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record RoomResponse(
        String name,
        LocalDate startDate,
        LocalTime startTime,
        String description,
        LolName manager,
        List<LolName> participants
) {
    public static RoomResponse from(final Room room) {
        return new RoomResponse(
                room.getName(),
                room.getStartDate(),
                room.getStartTime(),
                room.getDescription(),
                room.getManager().getLolName(),
                room.getRoomMembers().stream()
                        .map(roomMembers -> roomMembers.getMember().getLolName())
                        .toList()
        );
    }
}
