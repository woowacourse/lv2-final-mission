package finalmission.service;

import finalmission.domain.MemberRole;
import finalmission.dto.request.CreateReservationRequest;
import finalmission.dto.request.MemberInfo;
import finalmission.dto.request.MyReservationResponse;
import finalmission.dto.response.CreateReservationResponse;
import finalmission.dto.response.ReservationResponse;
import finalmission.entity.BlackList;
import finalmission.entity.MeetingRoom;
import finalmission.entity.Member;
import finalmission.entity.Reservation;
import finalmission.entity.ReservationTime;
import finalmission.exception.custom.CannotAccessException;
import finalmission.exception.custom.CannotRemoveException;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.exception.custom.NotAuthenticatedException;
import finalmission.exception.custom.NotExistedValueException;
import finalmission.repository.BlackListRepository;
import finalmission.repository.MeetingRoomRepository;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import finalmission.repository.ReservationTimeRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    BlackListRepository blackListRepository;

    @Mock
    ReservationTimeRepository reservationTimeRepository;

    @Mock
    MeetingRoomRepository meetingRoomRepository;

    @InjectMocks
    ReservationService reservationService;

    MemberInfo memberInfo;
    Member member;
    ReservationTime reservationTime;
    MeetingRoom meetingRoom;

    @BeforeEach
    void setup() {
        memberInfo = new MemberInfo(1L, "test", MemberRole.MEMBER);
        member = new Member(1L, "test@test.com", "test", "test", MemberRole.MEMBER);
        reservationTime = new ReservationTime(1L, LocalTime.of(10, 0));
        meetingRoom = new MeetingRoom(1L, "name", "desc", 10);
    }

    @Test
    @DisplayName("전체 예약 정보를 조회한다.")
    void findAllReservation() {
        //given
        LocalDate date = LocalDate.of(3000, 1, 1);
        when(reservationRepository.findAllFetch())
                .thenReturn(List.of(new Reservation(1L, member, date, reservationTime, meetingRoom)));

        //when
        List<ReservationResponse> actual = reservationService.findAllReservation();

        //then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("특정 사용자의 전체 예약 정보를 조회한다.")
    void findAllMyReservation() {
        //given
        LocalDate date = LocalDate.of(3000, 1, 1);
        when(reservationRepository.findAllByMemberId(anyLong()))
                .thenReturn(List.of(new Reservation(1L, member, date, reservationTime, meetingRoom)));

        MyReservationResponse notContained = new MyReservationResponse(2L,
                "test2", meetingRoom.getName(), meetingRoom.getDescribe(), date, reservationTime.getStartAt());

        //when
        List<MyReservationResponse> actual = reservationService.findAllMyReservation(memberInfo);

        //then
        assertAll(
                () -> assertThat(actual).hasSize(1),
                () -> assertThat(actual).doesNotContain(notContained)
        );
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void addReservation() {
        //given
        LocalDate date = LocalDate.of(3000, 1, 1);
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));
        when(blackListRepository.findByMember(any(Member.class)))
                .thenReturn(Optional.empty());
        when(meetingRoomRepository.findById(anyLong()))
                .thenReturn(Optional.of(meetingRoom));
        when(reservationTimeRepository.findById(anyLong()))
                .thenReturn(Optional.of(reservationTime));
        when(reservationRepository.existsByMeetingRoomIdAndDateAndReservationTimeId(anyLong(), any(LocalDate.class),
                anyLong()))
                .thenReturn(false);
        when(reservationRepository.save(any(Reservation.class)))
                .thenReturn(new Reservation(1L, member, date, reservationTime, meetingRoom));
        CreateReservationRequest request = new CreateReservationRequest(1L, date, 1L);

        //when
        CreateReservationResponse actual = reservationService.addReservation(request, memberInfo);

        //then
        CreateReservationResponse expected = new CreateReservationResponse(1L,
                meetingRoom.getName(), meetingRoom.getDescribe(), meetingRoom.getAvailablePeopleCount(),
                member.getName(),
                date, reservationTime.getStartAt());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID일 경우, 예외를 던진다.")
    void throwExceptionWhenNotExistedMemberId() {
        //given
        LocalDate date = LocalDate.of(3000, 1, 1);
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        CreateReservationRequest request = new CreateReservationRequest(1L, date, 1L);

        //when //then
        assertThatThrownBy(() -> reservationService.addReservation(request, memberInfo))
                .isInstanceOf(NotAuthenticatedException.class)
                .hasMessageContaining("존재하지 않는 유저입니다.");
    }

    @Test
    @DisplayName("블랙리스트에 존재하는 멤버 ID일 경우, 예외를 던진다.")
    void throwExceptionWhenContainedMemberIdInBlackList() {
        //given
        LocalDate date = LocalDate.of(3000, 1, 1);
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));
        when(blackListRepository.findByMember(any(Member.class)))
                .thenReturn(Optional.of(new BlackList(1L, member, "test")));
        CreateReservationRequest request = new CreateReservationRequest(1L, date, 1L);

        //when //then
        assertThatThrownBy(() -> reservationService.addReservation(request, memberInfo))
                .isInstanceOf(CannotAccessException.class)
                .hasMessageContaining("관리자에 의해 제한된 유저입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 회의실 ID일 경우, 예외를 던진다.")
    void throwExceptionWhenNotExistedMeetingRoomId() {
        //given
        LocalDate date = LocalDate.of(3000, 1, 1);
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));
        when(blackListRepository.findByMember(any(Member.class)))
                .thenReturn(Optional.empty());
        when(meetingRoomRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        CreateReservationRequest request = new CreateReservationRequest(1L, date, 1L);

        //when //then
        assertThatThrownBy(() -> reservationService.addReservation(request, memberInfo))
                .isInstanceOf(NotExistedValueException.class)
                .hasMessageContaining("존재하지 않는 회의실입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 예약 가능 시간 ID일 경우, 예외를 던진다.")
    void throwExceptionWhenNotExistedTimeId() {
        //given
        LocalDate date = LocalDate.of(3000, 1, 1);
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));
        when(blackListRepository.findByMember(any(Member.class)))
                .thenReturn(Optional.empty());
        when(meetingRoomRepository.findById(anyLong()))
                .thenReturn(Optional.of(meetingRoom));
        when(reservationTimeRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        CreateReservationRequest request = new CreateReservationRequest(1L, date, 1L);

        //when //then
        assertThatThrownBy(() -> reservationService.addReservation(request, memberInfo))
                .isInstanceOf(NotExistedValueException.class)
                .hasMessageContaining("존재하지 않는 예약 가능 시간입니다.");
    }

    @Test
    @DisplayName("같은 조건의 예약이 이미 존재하는 경우, 예외를 던진다.")
    void throwExceptionWhenExistedSameReservation() {
        //given
        LocalDate date = LocalDate.of(3000, 1, 1);
        when(memberRepository.findById(anyLong()))
                .thenReturn(Optional.of(member));
        when(blackListRepository.findByMember(any(Member.class)))
                .thenReturn(Optional.empty());
        when(meetingRoomRepository.findById(anyLong()))
                .thenReturn(Optional.of(meetingRoom));
        when(reservationTimeRepository.findById(anyLong()))
                .thenReturn(Optional.of(reservationTime));
        when(reservationRepository.existsByMeetingRoomIdAndDateAndReservationTimeId(anyLong(), any(LocalDate.class),
                anyLong()))
                .thenReturn(true);

        CreateReservationRequest request = new CreateReservationRequest(1L, date, 1L);

        //when //then
        assertThatThrownBy(() -> reservationService.addReservation(request, memberInfo))
                .isInstanceOf(DuplicatedValueException.class)
                .hasMessageContaining("이미 예약이 존재합니다.");
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        //given
        when(reservationRepository.existsById(anyLong()))
                .thenReturn(true);
        when(reservationRepository.existsByIdAndMemberId(anyLong(), anyLong()))
                .thenReturn(true);

        //when //then
        assertDoesNotThrow(() -> reservationService.deleteReservation(1L, memberInfo));
    }

    @Test
    @DisplayName("존재하지 않는 예약은 삭제할 수 없다.")
    void cannotDeleteNotExistedReservation() {
        //given
        when(reservationRepository.existsById(anyLong()))
                .thenReturn(false);

        //when //then
        assertThatThrownBy(() -> reservationService.deleteReservation(1L, memberInfo))
                .isInstanceOf(NotExistedValueException.class)
                .hasMessageContaining("존재하지 않는 예약입니다.");
    }

    @Test
    @DisplayName("자신의 예약이 아닌 예약은 삭제할 수 없다.")
    void cannotDeleteReservation() {
        //given
        when(reservationRepository.existsById(anyLong()))
                .thenReturn(true);
        when(reservationRepository.existsByIdAndMemberId(anyLong(), anyLong()))
                .thenReturn(false);

        //when //then
        assertThatThrownBy(() -> reservationService.deleteReservation(1L, memberInfo))
                .isInstanceOf(CannotRemoveException.class)
                .hasMessageContaining("예약자만 삭제할 수 있습니다.");
    }
}
