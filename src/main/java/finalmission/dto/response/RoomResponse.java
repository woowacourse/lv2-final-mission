package finalmission.dto.response;

import finalmission.domain.Room;
import finalmission.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

public record RoomResponse(
        LocalDate startDate,
        LocalDate endDate,
        LocalTime startTime,
        LocalTime endTime,
        Set<String> voters
) {
    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getStartDate(),
                room.getEndDate(),
                room.getStartTime(),
                room.getEndTime(),
                room.getTimes().stream()
                        .map(Time::getVoterName)
                        .collect(Collectors.toSet())
        );
    }
}
