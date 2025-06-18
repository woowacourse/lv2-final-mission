package finalmission.reservation.intrastructure.client.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    @Qualifier("sendgridMailClient")
    public RestClient sendgridMailClient(
            @Value("${sendgrid.url.base}") String url,
            @Value("${sendgrid.authorization}") String auth,
            @Value("${sendgrid.timeout.connect}") int connectTimeout,
            @Value("${sendgrid.timeout.read}") int readTimeout
    ) {
        return RestClient.builder()
                .baseUrl(url)
                .requestFactory(createRequestFactory(connectTimeout, readTimeout))
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", auth)
                .build();
    }

    private SimpleClientHttpRequestFactory createRequestFactory(final int connectTimeout, final int readTimeout) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(connectTimeout));
        requestFactory.setReadTimeout(Duration.ofSeconds(readTimeout));
        return requestFactory;
    }
}
