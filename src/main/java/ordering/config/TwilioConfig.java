package ordering.config;

import java.time.Duration;
import ordering.config.client.TwilioMailRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class TwilioConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_SCHEME = "Bearer ";

    @Value("${security.twilio.api-key}")
    private String secretKey;

    @Bean
    public TwilioMailRestClient mailRestClient(RestClient.Builder builder) {
        return new TwilioMailRestClient(builder
            .baseUrl("https://api.sendgrid.com/v3")
            .defaultHeader(AUTHORIZATION_HEADER, AUTHORIZATION_SCHEME + secretKey)
            .requestFactory(createRequestFactory())
            .build());
    }

    private static SimpleClientHttpRequestFactory createRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(1));
        requestFactory.setReadTimeout(Duration.ofSeconds(2));
        return requestFactory;
    }
}
