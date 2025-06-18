package finalmission.service;

import finalmission.dto.request.CreateReservationTimeRequest;
import finalmission.dto.response.CreateReservationTimeResponse;
import finalmission.dto.response.ReservationTimeResponse;
import finalmission.entity.ReservationTime;
import finalmission.exception.custom.CannotRemoveException;
import finalmission.exception.custom.DuplicatedValueException;
import finalmission.exception.custom.NotExistedValueException;
import finalmission.repository.ReservationRepository;
import finalmission.repository.ReservationTimeRepository;
import java.time.LocalTime;
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
class ReservationTimeServiceTest {

    @Mock
    ReservationTimeRepository reservationTimeRepository;

    @Mock
    ReservationRepository reservationRepository;

    @InjectMocks
    ReservationTimeService reservationTimeService;

    @Test
    @DisplayName("모든 예약 가능 시간 정보를 조회한다.")
    void findAllReservationTime() {
        //given
        when(reservationTimeRepository.findAll())
                .thenReturn(List.of(new ReservationTime(1L, LocalTime.of(10, 0))));

        //when
        List<ReservationTimeResponse> actual = reservationTimeService.findAllReservationTime();

        //then
        assertThat(actual).hasSize(1);
    }

    @Test
    @DisplayName("예약 가능 시간을 추가한다.")
    void addReservationTime() {
        //given
        when(reservationTimeRepository.existsByStartAt(any(LocalTime.class)))
                .thenReturn(false);
        when(reservationTimeRepository.save(any(ReservationTime.class)))
                .thenReturn(new ReservationTime(1L, LocalTime.of(10, 0)));

        CreateReservationTimeRequest request = new CreateReservationTimeRequest(LocalTime.of(10, 0));

        //when
        CreateReservationTimeResponse actual = reservationTimeService.addReservationTime(request);

        //then
        CreateReservationTimeResponse expected = new CreateReservationTimeResponse(1L, LocalTime.of(10, 0));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("중복된 시간을 가진 예약 가능 시간은 추가할 수 없다")
    void cannotAddDuplicateStartAt() {
        //given
        when(reservationTimeRepository.existsByStartAt(any(LocalTime.class)))
                .thenReturn(true);
        CreateReservationTimeRequest request = new CreateReservationTimeRequest(LocalTime.of(10, 0));

        //when //then
        Assertions.assertThatThrownBy(() -> reservationTimeService.addReservationTime(request))
                .isInstanceOf(DuplicatedValueException.class)
                .hasMessageContaining("이미 존재하는 예약 가능 시간입니다.");
    }

    @Test
    @DisplayName("예약 가능 시간을 삭제한다.")
    void deleteReservationTime() {
        //given
        when(reservationRepository.existsByReservationTimeId(any(Long.class)))
                .thenReturn(false);
        when(reservationTimeRepository.existsById(any(Long.class)))
                .thenReturn(true);

        //when //then
        assertDoesNotThrow(() -> reservationTimeService.deleteReservationTime(1L));
    }

    @Test
    @DisplayName("예약이 존재하는 예약 가능 시간은 삭제할 수 없다.")
    void cannotDeleteExistedReservation() {
        //given
        when(reservationRepository.existsByReservationTimeId(any(Long.class)))
                .thenReturn(true);

        //when //then
        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(1L))
                .isInstanceOf(CannotRemoveException.class)
                .hasMessageContaining("예약이 존재하는 예약 가능 시간은 제거할 수 없습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 예약 가능 시간은 삭제할 수 없다.")
    void cannotDeleteNotExisted() {
        //given
        when(reservationRepository.existsByReservationTimeId(any(Long.class)))
                .thenReturn(false);
        when(reservationTimeRepository.existsById(any(Long.class)))
                .thenReturn(false);

        //when //then
        assertThatThrownBy(() -> reservationTimeService.deleteReservationTime(1L))
                .isInstanceOf(NotExistedValueException.class)
                .hasMessageContaining("존재하지 않는 예약 가능 시간입니다.");
    }
}
