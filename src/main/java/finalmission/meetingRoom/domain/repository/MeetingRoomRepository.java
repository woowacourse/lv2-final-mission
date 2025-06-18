package finalmission.meetingRoom.domain.repository;

import finalmission.meetingRoom.domain.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
}
