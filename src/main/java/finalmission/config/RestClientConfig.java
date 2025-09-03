package finalmission.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${randommer-base-url}")
    private String baseUrl;
    @Value("${rest-client.connection-timeout}")
    private int connectTimeoutThreshold;
    @Value("${rest-client.read-timeout}")
    private int readTimeoutThreshold;

    @Bean
    public RestClient.Builder restClientBuilder() {
        final var clientFactory = new HttpComponentsClientHttpRequestFactory();
        clientFactory.setConnectTimeout(connectTimeoutThreshold);
        clientFactory.setReadTimeout(readTimeoutThreshold);

        return RestClient.builder()
                .requestFactory(clientFactory)
                .baseUrl(baseUrl);
    }
}
