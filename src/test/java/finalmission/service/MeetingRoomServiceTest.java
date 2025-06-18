package finalmission.service;

import finalmission.dto.request.CreateMeetingRoomRequest;
import finalmission.dto.response.CreateMeetingRoomResponse;
import finalmission.dto.response.MeetingRoomResponse;
import finalmission.entity.MeetingRoom;
import finalmission.exception.custom.CannotRemoveException;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.exception.custom.InvalidValueException;
import finalmission.exception.custom.NotExistedValueException;
import finalmission.repository.MeetingRoomRepository;
import finalmission.repository.ReservationRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeetingRoomServiceTest {

    @Mock
    MeetingRoomRepository meetingRoomRepository;

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    MeetingRoomService meetingRoomService;

    @Test
    @DisplayName("모든 회의실 정보를 조회한다.")
    void findAllMeetingRoom() {
        //given
        when(meetingRoomRepository.findAll())
                .thenReturn(List.of(new MeetingRoom(1L, "이름", "설명", 10)));

        //when
        List<MeetingRoomResponse> actual = meetingRoomService.findAllMeetingRoom();

        //then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("회의실을 추가한다.")
    void addMeetingRoom() {
        //given
        when(meetingRoomRepository.existsByName(any(String.class)))
                .thenReturn(false);
        when(meetingRoomRepository.save(any(MeetingRoom.class)))
                .thenReturn(new MeetingRoom(1L, "이름", "설명", 10));

        CreateMeetingRoomRequest request = new CreateMeetingRoomRequest("이름", "설명", 10);

        //when
        CreateMeetingRoomResponse actual = meetingRoomService.addMeetingRoom(request);

        //then
        CreateMeetingRoomResponse expected = new CreateMeetingRoomResponse(1L, "이름", "설명", 10);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("양수가 아닌 회의실 가용 인원을 가진 회의실은 추가할 수 없다")
    void cannotAddInvalidAvailablePeopleCount() {
        //given
        CreateMeetingRoomRequest request = new CreateMeetingRoomRequest("이름", "설명", 0);

        //when //then
        Assertions.assertThatThrownBy(() -> meetingRoomService.addMeetingRoom(request))
                .isInstanceOf(InvalidValueException.class)
                .hasMessageContaining("가용 인원은 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("중복된 회의실명을 가진 회의실은 추가할 수 없다")
    void cannotAddDuplicateName() {
        //given
        when(meetingRoomRepository.existsByName(any(String.class)))
                .thenReturn(true);
        CreateMeetingRoomRequest request = new CreateMeetingRoomRequest("이름", "설명", 10);

        //when //then
        Assertions.assertThatThrownBy(() -> meetingRoomService.addMeetingRoom(request))
                .isInstanceOf(DuplicatedValueException.class)
                .hasMessageContaining("중복된 회의실 명입니다.");
    }

    @Test
    @DisplayName("회의실을 삭제한다.")
    void deleteMeetingRoom() {
        //given
        when(reservationRepository.existsByMeetingRoomId(any(Long.class)))
                .thenReturn(false);
        when(meetingRoomRepository.existsById(any(Long.class)))
                .thenReturn(true);

        //when //then
        assertDoesNotThrow(() -> meetingRoomService.deleteMeetingRoom(1L));
    }

    @Test
    @DisplayName("예약이 존재하는 회의실은 삭제할 수 없다.")
    void cannotDeleteExistedReservation() {
        //given
        when(reservationRepository.existsByMeetingRoomId(any(Long.class)))
                .thenReturn(true);

        //when //then
        assertThatThrownBy(() -> meetingRoomService.deleteMeetingRoom(1L))
                .isInstanceOf(CannotRemoveException.class)
                .hasMessageContaining("예약이 존재하는 회의실은 제거할 수 없습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 회의실은 삭제할 수 없다.")
    void cannotDeleteNotExisted() {
        //given
        when(meetingRoomRepository.existsById(any(Long.class)))
                .thenReturn(false);

        //when //then
        assertThatThrownBy(() -> meetingRoomService.deleteMeetingRoom(1L))
                .isInstanceOf(NotExistedValueException.class)
                .hasMessageContaining("존재하지 않는 회의실입니다.");
    }
}
