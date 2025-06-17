package finalmission.global.restClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RandommerRestConfig {

    @Bean
    public RestClient randommerRestClient() {

        return RestClient.builder()
                .baseUrl("https://randommer.io/api")
                .defaultHeader("X-Api-Key", "67a455bef4a44ddd96ba407ea0a42d26")
                .build();
    }
}
