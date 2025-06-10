package ordering.service;

import ordering.config.client.TwilioMailRestClient;
import ordering.dto.request.TwilioMailSend;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final TwilioMailRestClient twilioMailRestClient;

    public MailService(TwilioMailRestClient twilioMailRestClient) {
        this.twilioMailRestClient = twilioMailRestClient;
    }

    public String sendMail() {
        // TODO: 메일 인증 후 구현
        TwilioMailSend request = null;

        return twilioMailRestClient.requestConfirmation(request);
    }
}
