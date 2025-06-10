package finalmission.reservation;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import finalmission.reservation.dto.ReservationRequest;
import finalmission.station.StationRepository;
import finalmission.subway.SubwayRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({ReservationService.class})
class ReservationServiceTest {
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceTest(ReservationService reservationService, ReservationRepository reservationRepository) {
        this.reservationService = reservationService;
        this.reservationRepository = reservationRepository;
    }

//    @DisplayName("Reservation 추가 테스트")
//    @Test
//    void createReservationTest() {
//        // given
//        ReservationRequest reservationRequest = new ReservationRequest(
//                LocalDate.now(),
//                1,
//                "A",
//                "신림",
//                "서울대입구"
//                );
//
//        // when
//        reservationService.createReservation(reservationRequest);
//
//        // then
//        boolean exist = reservationRepository.existsById(1L);
//        Assertions.assertThat(exist).isTrue();
//    }
}
