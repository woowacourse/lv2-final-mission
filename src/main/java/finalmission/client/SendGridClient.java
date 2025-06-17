package finalmission.client;

import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public final class SendGridClient implements MailClient {

    private final RestClient restClient;
    private final MailProperties mailProperties;

    public SendGridClient(MailProperties mailProperties) {
        restClient = RestClient.builder()
                .baseUrl("https://api.sendgrid.com/v3")
                .requestFactory(createRequestFactory())
                .build();
        this.mailProperties = mailProperties;
    }

    private SimpleClientHttpRequestFactory createRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(3));
        requestFactory.setReadTimeout(Duration.ofSeconds(35));
        return requestFactory;
    }

    @Override
    public void send(String to, String title, String content) {
        try {
            restClient.post()
                    .uri("/mail/send")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + mailProperties.apiKey())
                    .body(RequestSendMail.of(to, mailProperties.from(), title, content))
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception e) {
            throw new RuntimeException("메일 전송에 실패했습니다.", e);
        }
    }
}
