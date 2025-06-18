package finalmission.meetingRoom.application;

import finalmission.meetingRoom.application.dto.MeetingRoomResponse;
import finalmission.meetingRoom.domain.MeetingRoom;
import finalmission.meetingRoom.domain.repository.MeetingRoomRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;

    public List<MeetingRoomResponse> findAll() {
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
        return MeetingRoomResponse.from(meetingRooms);
    }
}
