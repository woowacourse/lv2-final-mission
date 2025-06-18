package finalmission.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import finalmission.domain.ReservationDateTime;
import finalmission.dto.request.ReservationDateTimeRequest;
import finalmission.exception.NotFoundDateTimeException;
import finalmission.infrastructure.ReservationDateTimeRepository;
import finalmission.service.ReservationDateTimeService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationDateTimeServiceTest {

    ReservationDateTime reservationDateTime1 = new ReservationDateTime(1L, LocalDate.of(2025,5,5), LocalTime.of(10,0));
    ReservationDateTime reservationDateTime2 = new ReservationDateTime(2L, LocalDate.of(2025,5,6), LocalTime.of(11,0));
    ReservationDateTime reservationDateTime3 = new ReservationDateTime(3L, LocalDate.of(2025,5,7), LocalTime.of(12,0));
    ReservationDateTime reservationDateTime4 = new ReservationDateTime(4L, LocalDate.of(2025,5,8), LocalTime.of(13,0));
    @Mock
    private ReservationDateTimeRepository reservationDateTimeRepository;
    @InjectMocks
    private ReservationDateTimeService reservationDateTimeService;

    @Test
    void 모든_예약날짜시간_조회() {
        //given
        List<ReservationDateTime> dateTimes = List.of(reservationDateTime1, reservationDateTime2, reservationDateTime3, reservationDateTime4);
        given(reservationDateTimeRepository.findAll()).willReturn(dateTimes);

        //when
        List<ReservationDateTime> foundDateTimes = reservationDateTimeService.findReservationDateTimes();

        //then
        assertThat(foundDateTimes).hasSize(4);
    }

    @Test
    void 예약시간_저장_테스트() {
        //given
        ReservationDateTimeRequest request = new ReservationDateTimeRequest(LocalDate.of(2025,5,5), LocalTime.of(10,0));
        given(reservationDateTimeRepository.save(any())).willReturn(reservationDateTime1);

        //when
        ReservationDateTime savedReservationDateTime = reservationDateTimeService.saveReservationDateTime(request);

        //then
        assertThat(savedReservationDateTime.getId()).isEqualTo(1L);
    }

    @Test
    void 존재하는_예약시간_삭제() {
        //given
        Long id = 1L;
        given(reservationDateTimeRepository.existsById(any())).willReturn(true);

        // when
        reservationDateTimeService.deleteReservationDateTime(id);

        // then
        verify(reservationDateTimeRepository).deleteById(id);
    }

    @Test
    void 없는_예약시간_삭제() {
        //given
        given(reservationDateTimeRepository.existsById(any())).willReturn(false);
        //when & then
        assertThatCode(()->reservationDateTimeService.deleteReservationDateTime(any()))
                .isInstanceOf(NotFoundDateTimeException.class)
                .hasMessage("예약 시간, 날짜를 찾을 수 없습니다.");
    }
}
