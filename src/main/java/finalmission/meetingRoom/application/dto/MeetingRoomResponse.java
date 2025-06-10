package finalmission.meetingRoom.application.dto;

import finalmission.meetingRoom.domain.MeetingRoom;
import java.util.List;

public record MeetingRoomResponse(Long id, String name, int minimumPersonnel, int maximumPersonnel, int maximumTime) {
    public static MeetingRoomResponse from(MeetingRoom meetingRoom) {
        return new MeetingRoomResponse(meetingRoom.getId(), meetingRoom.getName(), meetingRoom.getMinimumPersonnel(),
                meetingRoom.getMaximumPersonnel(), meetingRoom.getMaximumTime());
    }

    public static List<MeetingRoomResponse> from(List<MeetingRoom> meetingRooms) {
        return meetingRooms.stream()
                .map(MeetingRoomResponse::from)
                .toList();
    }
}
