package finalmission.member.infrastructure;

import finalmission.global.provider.RestClientProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class NameRestClientConfig {
    private final NameApiProperties nameApiProperties;

    @Bean(name = "nameApiRestClient")
    public RestClient nameRestClient() {
        return RestClientProvider.createRestClient(nameApiProperties)
                .defaultHeader("X-Api-Key", nameApiProperties.getSecretKey())
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
