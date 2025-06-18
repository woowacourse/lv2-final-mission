package finalmission.infrastructure;

import static org.assertj.core.api.Assertions.assertThatCode;

import finalmission.domain.Reservation;
import finalmission.repository.ReservationRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailGunClientTest {

    @Autowired
    private MailGunClient mailClient;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @Disabled
    void 회의실_예약_성공_메일을_전송한다() {
        // given
        Reservation reservation = reservationRepository.findById(1L)
                .orElseThrow();

        // when && then
        assertThatCode(() -> mailClient.sendReservationMail(reservation))
                .doesNotThrowAnyException();
    }
}
