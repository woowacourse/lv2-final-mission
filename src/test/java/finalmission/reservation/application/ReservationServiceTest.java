package finalmission.reservation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.fixture.MemberFixture;
import finalmission.meetingRoom.domain.MeetingRoom;
import finalmission.meetingRoom.domain.repository.MeetingRoomRepository;
import finalmission.member.domain.Member;
import finalmission.member.domain.repository.MemberRepository;
import finalmission.reservation.application.dto.ReservationRequest;
import finalmission.reservation.application.dto.ReservationResponse;
import finalmission.reservation.application.dto.ReservationUpdateRequest;
import finalmission.reservation.domain.repository.ReservationRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(ReservationService.class)
class ReservationServiceTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    private ReservationService reservationService;

    private Member member;
    private MeetingRoom meetingRoom;

    @BeforeEach
    void setUp() {
        member = MemberFixture.createMember("test", "test@example.com", "password");
        memberRepository.save(member);

        meetingRoom = new MeetingRoom("test", 1);
        meetingRoomRepository.save(meetingRoom);
    }

    @DisplayName("예약 생성 성공")
    @Test
    void create() {
        ReservationRequest request = new ReservationRequest(
                meetingRoom.getId(),
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        ReservationResponse response = reservationService.create(request, member.getId());

        assertThat(response.member().id()).isEqualTo(member.getId());
        assertThat(response.meetingRoom().id()).isEqualTo(meetingRoom.getId());
    }

    @DisplayName("중복 시간 예약 시 실패")
    @Test
    void create_fail_with_duplicateTime() {
        ReservationRequest request = new ReservationRequest(
                meetingRoom.getId(),
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );
        reservationService.create(request, member.getId());

        assertThatThrownBy(() -> reservationService.create(request, member.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 예약 중인 시간입니다.");
    }

    @DisplayName("내 예약 조회 성공")
    @Test
    void getMyReservation() {
        reservationService.create(
                new ReservationRequest(
                        meetingRoom.getId(),
                        LocalDate.now().plusDays(1),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                ),
                member.getId()
        );

        List<ReservationResponse> result = reservationService.getMyReservation(member.getId());

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().member().id()).isEqualTo(member.getId());
    }

    @DisplayName("회의실 예약 조회 성공")
    @Test
    void getRoomReservation() {
        reservationService.create(
                new ReservationRequest(
                        meetingRoom.getId(),
                        LocalDate.now().plusDays(1),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                ),
                member.getId()
        );

        List<ReservationResponse> result = reservationService.getRoomReservation(meetingRoom.getId());

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().meetingRoom().id()).isEqualTo(meetingRoom.getId());
    }

    @DisplayName("타인의 예약 삭제 시도 시 실패")
    @Test
    void delete_fail_with_unauthorized() {
        ReservationResponse response = reservationService.create(
                new ReservationRequest(
                        meetingRoom.getId(),
                        LocalDate.now().plusDays(1),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                ),
                member.getId()
        );

        Long invalidMemberId = member.getId() + 1;
        assertThatThrownBy(() -> reservationService.delete(invalidMemberId, response.id()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("삭제 권한이 없습니다.");
    }

    @DisplayName("예약 시간 수정 성공")
    @Test
    void update_success() {
        ReservationResponse created = reservationService.create(
                new ReservationRequest(
                        meetingRoom.getId(),
                        LocalDate.now().plusDays(1),
                        LocalTime.of(10, 0),
                        LocalTime.of(11, 0)
                ),
                member.getId()
        );

        ReservationUpdateRequest updateRequest = new ReservationUpdateRequest(
                LocalDate.now().plusDays(1).plusDays(1),
                LocalTime.of(14, 0),
                LocalTime.of(15, 0)
        );
        ReservationResponse updated = reservationService.update(
                created.id(),
                updateRequest,
                member.getId()
        );

        assertThat(updated.date()).isEqualTo(LocalDate.now().plusDays(2));
        assertThat(updated.startAt()).isEqualTo(LocalTime.of(14, 0));
    }

    @DisplayName("종료 시간이 시작 시간보다 빠를 경우 실패")
    @Test
    void create_fail_with_invalidTime() {
        ReservationRequest invalidRequest = new ReservationRequest(
                meetingRoom.getId(),
                LocalDate.now().plusDays(1),
                LocalTime.of(12, 0),
                LocalTime.of(10, 0)
        );

        assertThatThrownBy(() -> reservationService.create(invalidRequest, member.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
