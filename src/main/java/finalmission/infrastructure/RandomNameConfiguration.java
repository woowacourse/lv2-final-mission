package finalmission.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RandomNameConfiguration {

    private final RestClient.Builder builder;

    public RandomNameConfiguration(final RestClient.Builder builder) {
        this.builder = builder;
    }

    public RestClient.Builder restClientBuilder() {
        return builder.requestFactory(requestFactory());
    }

    private ClientHttpRequestFactory requestFactory() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(2000);
        return factory;
    }

    @Bean
    public RandomNameClient randomNameClient() {
        return new RandomNameClient(restClientBuilder());
    }
}
