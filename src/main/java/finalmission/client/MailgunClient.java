package finalmission.client;

import finalmission.client.dto.SendEmailRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

public class MailgunClient implements MailClient {

    private static final Logger log = LoggerFactory.getLogger(MailgunClient.class);

    private static final String FROM = "postmaster@sandbox493a7c6d7f3b418ba011700537536d77.mailgun.org";

    private final RestClient restClient;

    public MailgunClient(final RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public void sendEmail(final SendEmailRequest request) {
        log.info("Mailgun 이메일 전송 API 시작 : {}", request.to());

        restClient.post()
                .uri(builder -> builder
                        .queryParam("from", FROM)
                        .queryParam("to", request.to())
                        .queryParam("subject", request.subject())
                        .queryParam("text", request.content())
                        .build()
                )
                .retrieve()
                .onStatus(HttpStatusCode::isError,
                        (req, res) ->
                                log.warn("Mailgun 메일 전송 실패 - status: {}, details: {}", res.getStatusCode().value(),
                                        res.getBody()))
                .toBodilessEntity();
    }
}
