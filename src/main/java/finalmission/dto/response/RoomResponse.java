package finalmission.dto.response;

import finalmission.domain.Room;
import finalmission.domain.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

public record RoomResponse(
        LocalDate startDate,
        LocalDate endDate,
        LocalTime startTime,
        LocalTime endTime,
        boolean isAnonymousRoom,
        Set<String> voterNames
) {
    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getStartDate(),
                room.getEndDate(),
                room.getStartTime(),
                room.getEndTime(),
                room.isAnonymous(),
                room.getVotes().stream()
                        .map(Vote::getVoterName)
                        .collect(Collectors.toSet())
        );
    }
}
