package finalmission.config;

import finalmission.infrastructure.randommer.RandommerRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RandommerRestClientConfig {

    @Bean
    public RandommerRestClient nameGeneratorRestClient(RestClient.Builder builder) {
        return new RandommerRestClient(builder
                .baseUrl("https://randommer.io/api/Name")
                .build());
    }
}
