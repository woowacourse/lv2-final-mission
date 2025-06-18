package finalmission.controller.dto;

import finalmission.domain.Member;
import finalmission.domain.Room;
import java.time.LocalDate;
import java.time.LocalTime;

public record RoomCreateRequest(
        String name,
        LocalDate startDate,
        LocalTime startTime,
        String description
) {

    public Room toRoom(final Member manager) {
        return new Room(name, startDate, startTime, description, manager);
    }
}
