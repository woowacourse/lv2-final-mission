package finalmission.global.config;

import finalmission.infrastructure.RandomNameClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    @Bean
    public RandomNameClient randomNameClient() {
        return new RandomNameClient(RestClient.builder()
                .baseUrl("https://randommer.io/api/Name?nameType=fullname&quantity=1").build());
    }
}
