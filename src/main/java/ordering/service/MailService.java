package ordering.service;

import ordering.config.client.TwilioMailRestClient;
import ordering.dto.request.TwilioMailSend;
import ordering.dto.response.OrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final TwilioMailRestClient twilioMailRestClient;

    @Value("${security.twilio.to.email}")
    private String toEmail;

    @Value("${security.twilio.from.email}")
    private String fromEmail;

    public MailService(TwilioMailRestClient twilioMailRestClient) {
        this.twilioMailRestClient = twilioMailRestClient;
    }

    public String sendMail(OrderResponse orderResponse) {
        String content = orderResponse.toText();

        TwilioMailSend request = TwilioMailSend.from(toEmail, "발주 내역이 도착했습니다.", fromEmail, content);

        return twilioMailRestClient.requestConfirmation(request);
    }
}
