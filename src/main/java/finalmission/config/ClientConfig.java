package finalmission.config;

import finalmission.reservation.infrastructure.PublishHolidayClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    @Value("${holiday.secret-key}")
    private String secretKey;

    @Value("${holiday.base-url}")
    private String baseUrl;

    @Bean
    public PublishHolidayClient publishHolidayWithRestClient() {
        return new PublishHolidayClient(
                RestClient.builder().baseUrl(baseUrl).build(),
                secretKey
        );
    }
}
