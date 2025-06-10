package finalmission;

public record RoomCreateResponse(
        String roomId
) {
    public static RoomCreateResponse from(Room room) {
        return new RoomCreateResponse(room.getId());
    }
}
