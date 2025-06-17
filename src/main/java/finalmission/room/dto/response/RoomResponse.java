package finalmission.room.dto.response;

import finalmission.room.entity.Room;

public record RoomResponse(
        Long id,
        String name
) {
    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getName()
        );
    }
}
