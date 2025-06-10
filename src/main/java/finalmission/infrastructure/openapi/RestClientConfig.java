package finalmission.infrastructure.openapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private final String requestUrl;

    public RestClientConfig(@Value("${openapi.public-holidays.request-url}") final String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Bean
    public HolidayApi openApiProcessor() {
        final RestClient restClient = RestClient.builder()
                .build();

        return new HolidayApi(requestUrl, restClient);
    }
}
