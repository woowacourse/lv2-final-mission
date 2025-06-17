package finalmission.thirdparty.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RandomNameClientConfig {

    // TODO: 주입 방식으로 변경
    @Value("${random.name.secret-key}")
    private String randomNameSecretKey;

    @Bean
    public RestClient randomNameClient() {
        return RestClient.builder()
                .baseUrl("https://randommer.io/api/Name")
                .defaultHeader("X-Api-Key", "36615ccb4b0246b9bda9bfae7346f9af")
                .build();
    }
}
