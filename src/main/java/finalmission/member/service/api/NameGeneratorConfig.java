package finalmission.member.service.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class NameGeneratorConfig {

    private static final String BASE_URL = "https://randommer.io/api/Name";
    private static final String X_API_KEY = "X-Api-Key";
    private final String secretKey;

    public NameGeneratorConfig(@Value("${name.generator.api.key}") String secretKey) {
        this.secretKey = secretKey;
    }

    @Bean
    public RestClient nameGeneratorRestClient() {
        return RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(X_API_KEY, secretKey)
                .build();
    }

}
