package finalmission.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {

    @Value("${email.secret.key}")
    private String emailApiKey;

//    @Bean
//    public RestClient emailRestClient() {
//        return RestClient.builder()
//                .baseUrl("https://api.sendgrid.com")
//                .defaultHeader("Content-Type", "application/json")
//                .defaultHeader("Authorization", "Bearer " + emailApiKey)
//                .build();
//    }

    @Bean
    public RestClient publicHolidayRestClient() {
        return RestClient.builder()
                .baseUrl("https://apis.data.go.kr")
                .build();
    }
}
