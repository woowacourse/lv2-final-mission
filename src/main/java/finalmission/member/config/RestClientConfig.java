package finalmission.member.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${api.randommer.api-key}")
    private String apiKey;

    @Bean
    public RestClient randommerClient() {
        return RestClient.builder()
                .baseUrl("https://randommer.io/api/")
                .defaultHeader("X-Api-Key", apiKey)
                .build();
    }
}
