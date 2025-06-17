package finalmission.room.dto.request;

import finalmission.room.entity.Room;

public record RoomRequest(
        String name
) {
    public Room toEntity() {
        return new Room(name);
    }
}
