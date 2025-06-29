package finalmission.email.infrastructure.twilio;

import finalmission.email.domain.EmailClient;
import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
import finalmission.reservation.domain.ReservationSlot;
import finalmission.reservation.domain.ReservationTime;
import finalmission.restaurant.domain.Restaurant;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled // Sendgrid 이메일 전송 기능은 실제로 이메일을 전송하므로 테스트를 비활성화합니다.
@SpringBootTest
class TwilioEmailClientTest {

    @Autowired
    private EmailClient emailClient;

    @Test
    void 음식점_예약_성공_이메일_전송_테스트() {
        final SendEmailRequest request = SendEmailRequest.confirmReservation("threepebbles@naver.com");
        emailClient.sendEmail(request);
    }

    @Test
    void 음식점_예약_대기_알림_이메일_전송_테스트() {
        // given
        final String emailTo = "threepebbles@naver.com";
        final Restaurant restaurant = new Restaurant(
                "차이나 스토리",
                "역시 차스입니다.",
                "서울시 어딘가1",
                "02-1234-5678",
                20
        );
        final ReservationTime reservationTime = new ReservationTime(LocalTime.of(12, 0));
        final ReservationSlot reservationSlot = new ReservationSlot(
                LocalDate.now().plusDays(1),
                reservationTime,
                restaurant,
                20
        );

        // when
        final SendEmailRequest request = SendEmailRequest.alarmForWaiting(emailTo, reservationSlot);
        emailClient.sendEmail(request);
    }
}