package finalmission.dto.response;

import finalmission.domain.Room;

public record RoomCreateResponse(
        String roomId
) {
    public static RoomCreateResponse from(Room room) {
        return new RoomCreateResponse(room.getId().value());
    }
}
