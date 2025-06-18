package finalmission.controller.dto;

import finalmission.domain.Room;

public record RoomResponse(
        Long id,
        String name
) {

    public RoomResponse(Room room) {
        this(
                room.getId(),
                room.getName()
        );
    }
}
