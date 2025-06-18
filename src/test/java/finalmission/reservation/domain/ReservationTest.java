package finalmission.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.fixture.MemberFixture;
import finalmission.meetingRoom.domain.MeetingRoom;
import finalmission.member.domain.Member;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationTest {

    @DisplayName("유효한 파라미터로 Reservation 객체를 생성한다")
    @Test
    void createWithValidParameters() {
        // given
        Member member = MemberFixture.createMember("test", "test@test.com", "1234");
        MeetingRoom meetingRoom = new MeetingRoom("test", 1);
        ReservationDate date = new ReservationDate(LocalDate.now().plusDays(1));
        StartAt startAt = new StartAt(LocalTime.of(10, 0));
        EndAt endAt = new EndAt(LocalTime.of(11, 0));

        // when & then
        assertThatCode(() -> new Reservation(member, meetingRoom, date, startAt, endAt))
                .doesNotThrowAnyException();
    }

    @DisplayName("시작 시간이 종료 시간보다 늦으면 예외가 발생한다")
    @Test
    void validateStartTimeAfterEndTime() {
        // given
        Member member = MemberFixture.createMember("test", "test@test.com", "1234");
        MeetingRoom meetingRoom = new MeetingRoom("test", 1);
        ReservationDate date = new ReservationDate(LocalDate.now().plusDays(1));
        StartAt startAt = new StartAt(LocalTime.of(12, 0));
        EndAt endAt = new EndAt(LocalTime.of(11, 0));

        Reservation reservation = new Reservation(member, meetingRoom, date, startAt, endAt);

        // when & then
        assertThatThrownBy(reservation::checkValidTime)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("과거 시간에는 예약할 수 없다")
    @Test
    void validatePastTime() {
        // given
        Member member = MemberFixture.createMember("test", "test@test.com", "1234");
        MeetingRoom meetingRoom = new MeetingRoom("test", 1);
        ReservationDate date = new ReservationDate(LocalDate.now().minusDays(1));
        StartAt startAt = new StartAt(LocalTime.of(10, 0));
        EndAt endAt = new EndAt(LocalTime.of(11, 0));

        Reservation reservation = new Reservation(member, meetingRoom, date, startAt, endAt);

        // when & then
        assertThatThrownBy(reservation::checkValidTime)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
