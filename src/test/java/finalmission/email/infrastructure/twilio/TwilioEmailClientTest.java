package finalmission.email.infrastructure.twilio;

import finalmission.email.domain.EmailClient;
import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
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
    void 이메일_전송_테스트() {
        final SendEmailRequest request = SendEmailRequest.confirmReservation("threepebbles@naver.com");
        emailClient.sendEmail(request);
    }
}