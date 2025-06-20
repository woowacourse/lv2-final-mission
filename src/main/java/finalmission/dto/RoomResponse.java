package finalmission.dto;

import finalmission.domain.Room;

public record RoomResponse(
        long id,
        String name,
        int maxNumberOfPeople
) {

    public static RoomResponse from(final Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName(),
                room.getMaxNumberOfPeople()
        );
    }
}
