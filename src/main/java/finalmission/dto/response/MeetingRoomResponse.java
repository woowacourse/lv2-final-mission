package finalmission.dto.response;

import finalmission.entity.MeetingRoom;

public record MeetingRoomResponse(Long id, String name, String describe, Integer availableCount) {

    public static MeetingRoomResponse from(final MeetingRoom meetingRoom) {
        return new MeetingRoomResponse(meetingRoom.getId(),
                meetingRoom.getName(),
                meetingRoom.getDescribe(),
                meetingRoom.getAvailablePeopleCount());
    }
}
