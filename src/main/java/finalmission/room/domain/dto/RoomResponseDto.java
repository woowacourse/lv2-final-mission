package finalmission.room.domain.dto;

import finalmission.room.domain.Room;

public record RoomResponseDto(Long id, String name) {

    public static RoomResponseDto from (Room room) {
        return new RoomResponseDto(room.getId(), room.getName());
    }
}
