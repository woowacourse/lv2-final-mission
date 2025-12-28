package finalmission.mungPlan.infra.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WeatherApiProperties.class)
public class WeatherClientConfig {

    @Bean
    public WeatherClient tossRestClient(final WeatherApiProperties weatherApiProperties) {
        RestClient restClient = RestClient.builder()
                .baseUrl(weatherApiProperties.getBaseUrl())
                .build();

        return new WeatherClient(restClient, weatherApiProperties.getServiceKey());
    }
}
