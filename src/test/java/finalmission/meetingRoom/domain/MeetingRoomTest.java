package finalmission.meetingRoom.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MeetingRoomTest {

    @DisplayName("이름과 최대시간으로 회의실을 생성한다")
    @Test
    void create() {
        // given
        String name = "test";
        int maximumTime = 1;

        // when & then
        assertThatCode(() -> new MeetingRoom(name, maximumTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("최대 시간을 초과한다면 예외를 발생시킨다")
    @Test
    void checkAvailableTime() {
        // given
        // 최대 예약 시간 1시간인 회의실
        MeetingRoom meetingRoom = new MeetingRoom("test", 1);

        // when
        LocalTime startAt = LocalTime.of(12, 0);
        LocalTime validEndAt = LocalTime.of(13, 0); // 1시간
        LocalTime invalidEndAt = LocalTime.of(13, 1); // 1시간 1분

        // then
        assertThatCode(() -> meetingRoom.checkAvailableTime(startAt, validEndAt))
                .doesNotThrowAnyException();
        assertThatCode(() -> meetingRoom.checkAvailableTime(startAt, invalidEndAt))
                .isInstanceOf(IllegalArgumentException.class);
    }

}