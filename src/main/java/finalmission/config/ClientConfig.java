package finalmission.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    private final RandommerProperties randommerProperties;

    public ClientConfig(final RandommerProperties randommerProperties) {
        this.randommerProperties = randommerProperties;
    }

    @Bean(name = "randommerRestClient")
    public RestClient createRandommerClient() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(randommerProperties.connectTimeout());
        factory.setReadTimeout(randommerProperties.readTimeout());

        return RestClient.builder()
                .baseUrl(randommerProperties.baseUrl())
                .defaultHeader("X-Api-Key", randommerProperties.apiKey())
                .requestFactory(factory)
                .build();
    }
}
