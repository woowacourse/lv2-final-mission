package finalmission.dto.response;

import finalmission.entity.MeetingRoom;

public record CreateMeetingRoomResponse(Long id, String name, String describe, Integer availableCount) {

    public static CreateMeetingRoomResponse from(final MeetingRoom meetingRoom) {
        return new CreateMeetingRoomResponse(meetingRoom.getId(),
                meetingRoom.getName(),
                meetingRoom.getDescribe(),
                meetingRoom.getAvailablePeopleCount());
    }
}