package finalmission.business.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import finalmission.business.model.entity.Member;
import finalmission.business.model.entity.Reservation;
import finalmission.infrastructure.repository.ReservationRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("전체 예약 조회 테스트 - Mocking")
    void test() {
        // given
        Member ddiyong = Member.create("띠용", "ddiyong@gmail.com", "1234");
        String passportId1 = "DDI1YONG2";
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        String incheon = "인천국제공항";
        String osaka = "간사이국제공항";
        String flightCode = "BHANG123";
        Reservation reservation = Reservation.create(ddiyong, passportId1,
                tomorrow, tomorrow.plusHours(2), incheon, osaka,
                flightCode);
        Reservation reservation2 = Reservation.create(ddiyong, passportId1,
                tomorrow.plusHours(2), tomorrow.plusHours(4), osaka, incheon,
                flightCode);

        when(reservationRepository.findReservationsByMemberId(1L)).thenReturn(List.of(reservation, reservation2));

        // when & then
        assertThat(reservationService.getReservationsByMemberId(1L)).containsExactly(reservation, reservation2);
    }

}
