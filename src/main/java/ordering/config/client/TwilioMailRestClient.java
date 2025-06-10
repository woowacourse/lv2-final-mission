package ordering.config.client;

import ordering.dto.request.TwilioMailSend;
import org.springframework.web.client.RestClient;

public class TwilioMailRestClient {

    private final RestClient restClient;

    public TwilioMailRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String requestConfirmation(TwilioMailSend twilioMailSend) {
        return restClient.post()
            .uri("/mail/send")
            .body(twilioMailSend)
            .retrieve()
            .body(String.class);
    }
}
