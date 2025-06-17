package finalmission.client.config;

import finalmission.client.MailgunClient;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    private static final String BASE_URL = "https://api.mailgun.net/v3/sandbox493a7c6d7f3b418ba011700537536d77.mailgun.org/messages";

    private final int connectionTimeout;
    private final int readTimeout;
    private final String apiKey;

    public ClientConfig(@Value("${api.connection-timeout}") int connectionTimeout,
                        @Value("${api.read-timeout}") int readTimeout,
                        @Value("${mailgun.api-key}") String apiKey) {
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
        this.apiKey = apiKey;
    }

    @Bean
    public MailgunClient mailgunClient() {
        final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectionTimeout);
        requestFactory.setReadTimeout(readTimeout);
        return new MailgunClient(
                RestClient.builder()
                        .baseUrl(BASE_URL)
                        .defaultHeader("Authorization", getBasicAuthorizationValue())
                        .requestFactory(requestFactory)
                        .build()
        );
    }

    private String getBasicAuthorizationValue() {
        return "Basic " + Base64.getEncoder().encodeToString(("api:" + apiKey).getBytes(StandardCharsets.UTF_8));
    }
}

