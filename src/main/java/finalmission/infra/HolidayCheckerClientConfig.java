package finalmission.infra;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HolidayCheckerClientConfig {

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder()
            .defaultHeader("content-type", "application/xml")
            .defaultHeader("Accept", "*/*;q=0.9")
            .connectTimeout(Duration.ofSeconds(5))
            .readTimeout(Duration.ofSeconds(30));
    }
}
