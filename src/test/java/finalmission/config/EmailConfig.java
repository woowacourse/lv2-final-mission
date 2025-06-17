package finalmission.config;

import finalmission.email.domain.EmailClient;
import finalmission.email.infrastructure.twilio.TwilioEmailClient;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@TestConfiguration
public class EmailConfig {

    private final String baseUrl;
    private final String secretKey;

    public EmailConfig(
            @Value("${email.twilio.base-url}") final String baseUrl,
            @Value("${email.twilio.secret-key}") final String secretKey
    ) {
        this.baseUrl = baseUrl;
        this.secretKey = secretKey;
    }

    @Bean
    public EmailClient emailClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(3));
        requestFactory.setReadTimeout(Duration.ofSeconds(2));

        return new TwilioEmailClient(
                secretKey,
                RestClient.builder()
                        .requestFactory(requestFactory)
                        .baseUrl(baseUrl)
                        .build()
        );
    }
}
