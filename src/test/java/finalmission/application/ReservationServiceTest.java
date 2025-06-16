package finalmission.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import finalmission.application.config.TestConfig;
import finalmission.domain.ReservationRepository;
import finalmission.domain.ReservationStatus;
import finalmission.domain.Room;
import finalmission.domain.ScheduleRepository;
import finalmission.presentation.dto.ReservationRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Import(TestConfig.class)
@Transactional
class ReservationServiceTest {

    @Autowired
    private ReservationService sut;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    void 예약을_저장한다() {
        // given
        var reservationRequest = createReservationRequest(LocalTime.of(18, 0));

        // when
        var result = sut.saveReservation(reservationRequest);

        // then
        var savedReservation = reservationRepository.findByTicket(result).get();
        assertSoftly(softly -> {
            softly.assertThat(savedReservation.getTicket()).isNotNull();
            softly.assertThat(savedReservation.getDetails().crew()).isEqualTo(reservationRequest.crew());
            softly.assertThat(savedReservation.getDetails().description()).isEqualTo(reservationRequest.description());
            softly.assertThat(savedReservation.getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
        });
    }

    @Test
    void 티켓을_반환한다() {
        // given
        var reservationRequest = createReservationRequest(LocalTime.of(18, 0));

        // when
        var result = sut.saveReservation(reservationRequest);

        // then
        var savedSchedule = scheduleRepository.findById(result).get();
        assertSoftly(softly -> {
            softly.assertThat(savedSchedule.room().getTitle()).isEqualTo(reservationRequest.room());
            softly.assertThat(savedSchedule.date()).isEqualTo(reservationRequest.date());
            softly.assertThat(savedSchedule.time()).isEqualTo(reservationRequest.time());
        });
    }

    @Test
    void 이미_예약된_스케줄이면_예약할_수_없다() {
        // given
        var reservationRequest = createReservationRequest(LocalTime.of(18, 0));
        sut.saveReservation(reservationRequest);

        // when // then
        assertThatThrownBy(() -> sut.saveReservation(reservationRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이미 예약된 시간입니다.");
    }

    @Test
    void 기념일이면_예약할_수_없다() {
        // given
        var reservationRequest = createReservationRequest(LocalDate.of(2025, 5, 5));

        // when // then
        assertThatThrownBy(() -> sut.saveReservation(reservationRequest))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("기념일에는 예약할 수 없습니다.");
    }

    @Test
    void 예약들을_반환한다() {
        // given
        sut.saveReservation(createReservationRequest(LocalTime.of(17, 0)));
        sut.saveReservation(createReservationRequest(LocalTime.of(18, 0)));

        // when
        var result = sut.findAllReservation();

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    void 티켓으로_예약을_반환한다() {
        // given
        var reservationRequest = createReservationRequest(
            Room.IMPACT, LocalDate.of(2025, 6, 10), LocalTime.of(18, 0));
        var ticket = sut.saveReservation(reservationRequest);

        // when
        var result = sut.findReservation(ticket);

        // then
        assertSoftly(softly -> {
            softly.assertThat(result.room()).isEqualTo(reservationRequest.room());
            softly.assertThat(result.date()).isEqualTo(reservationRequest.date());
            softly.assertThat(result.time()).isEqualTo(reservationRequest.time());
        });
    }

    @Test
    void 취소된_예약은_스케줄을_반환할_수_없다() {
        // given
        var reservationRequest = createReservationRequest(LocalTime.of(18, 0));
        var ticket = sut.saveReservation(reservationRequest);
        sut.cancelReservation(ticket);

        // when // then
        assertThatThrownBy(() -> sut.findReservation(ticket))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("취소된 예약 입니다.");
    }

    @Test
    void 티켓으로_예약을_취소한다() {
        // given
        var reservationRequest = createReservationRequest(LocalTime.of(18, 0));
        var ticket = sut.saveReservation(reservationRequest);

        // when
        var result = sut.cancelReservation(ticket);

        // then
        assertThat(result.status()).isEqualTo(ReservationStatus.CANCELED.getTitle());
    }

    private ReservationRequest createReservationRequest(LocalDate date) {
        return new ReservationRequest(Room.IMPACT.getTitle(), date, LocalTime.of(18, 0), "머피", "스터디");
    }

    private ReservationRequest createReservationRequest(LocalTime time) {
        return new ReservationRequest(Room.IMPACT.getTitle(), LocalDate.of(2025, 6, 10), time, "머피", "스터디");
    }

    private ReservationRequest createReservationRequest(
        Room room,
        LocalDate date,
        LocalTime time
    ) {
        return new ReservationRequest(room.getTitle(), date, time, "머피", "스터디");
    }
}
