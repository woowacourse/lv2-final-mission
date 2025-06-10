package finalmission.email.config;

import finalmission.email.domain.EmailClient;
import finalmission.email.infrastructure.twilio.TwilioEmailClient;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private final String baseUrl;
    private final String secretKey;

    public RestClientConfig(
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
