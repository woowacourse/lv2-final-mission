package finalmission.meetingRoom.application;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.meetingRoom.application.dto.MeetingRoomResponse;
import finalmission.meetingRoom.domain.MeetingRoom;
import finalmission.meetingRoom.domain.repository.MeetingRoomRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@Import(MeetingRoomService.class)
class MeetingRoomServiceTest {

    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private MeetingRoomService meetingRoomService;

    @DisplayName("존재하는 회의실을 모두 반환한다")
    @Test
    void findAll() {
        // given
        MeetingRoom meetingRoom1 = new MeetingRoom("test1", 1);
        MeetingRoom meetingRoom2 = new MeetingRoom("test2", 1);
        MeetingRoom meetingRoom3 = new MeetingRoom("test3", 1);

        meetingRoomRepository.save(meetingRoom1);
        meetingRoomRepository.save(meetingRoom2);
        meetingRoomRepository.save(meetingRoom3);

        // when
        List<MeetingRoomResponse> responses = meetingRoomService.findAll();

        // then
        assertThat(responses).hasSize(3);
    }
}
