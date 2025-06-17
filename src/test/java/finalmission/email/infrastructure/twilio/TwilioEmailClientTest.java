package finalmission.email.infrastructure.twilio;

import finalmission.email.domain.EmailClient;
import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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