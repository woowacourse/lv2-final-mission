package finalmission.application;


import static org.assertj.core.api.SoftAssertions.assertSoftly;

import finalmission.domain.ReservationRepository;
import finalmission.domain.ScheduleRepository;
import finalmission.dto.ReservationRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    void 예약을_저장한다() {
        // given
        var reservationRequest = new ReservationRequest("임팩트", LocalDate.of(2025, 06, 10),
            LocalTime.of(18, 0), "머피", "스터디");

        // when
        var ticket = reservationService.saveReservation(reservationRequest);

        // then
        var savedReservation = reservationRepository.findById(ticket).get();
        assertSoftly(softly -> {
            softly.assertThat(savedReservation.getTicket()).isNotNull();
            softly.assertThat(savedReservation.getCrew()).isEqualTo(reservationRequest.crew());
            softly.assertThat(savedReservation.getDescription()).isEqualTo(
                reservationRequest.description());
        });
    }

    @Test
    void 티켓을_반환한다() {
        // given
        var reservationRequest = new ReservationRequest("임팩트", LocalDate.of(2025, 06, 10),
            LocalTime.of(18, 0), "머피", "스터디");

        // when
        var ticket = reservationService.saveReservation(reservationRequest);

        // then
        assertSoftly(softly -> {
            var savedSchedule = scheduleRepository.findByTicket(ticket);
            softly.assertThat(savedSchedule.room()).isEqualTo(reservationRequest.room());
            softly.assertThat(savedSchedule.date()).isEqualTo(reservationRequest.date());
            softly.assertThat(savedSchedule.time()).isEqualTo(reservationRequest.time());
        });
    }

    @Test
    void 이미_예약된_시간이면_예약할수없다() {

    }
}
