package finalmission.infrastructure.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@Setter
public class ClientConfig {

    @Value("${open-api.holiday.base-url}")
    private String holidayBaseUrl;

    @Value("${open-api.holiday.connection-timeout}")
    private int connectionTimeout;

    @Value("${open-api.holiday.read-timeout}")
    private int readTimeout;

    @Value("${open-api.holiday.secret-key}")
    private String holidaySecretKey;

    @Bean
    public HolidayClient holidayClient() {
        return new HolidayClient(
                RestClient.builder()
                        .baseUrl(holidayBaseUrl)
                        .requestFactory(createRequestFactory())
                        .build(),
                new ObjectMapper(),
                holidaySecretKey
        );
    }

    private ClientHttpRequestFactory createRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        factory.setConnectTimeout(Duration.ofSeconds(connectionTimeout));
        factory.setReadTimeout(Duration.ofSeconds(readTimeout));

        return factory;
    }
}
