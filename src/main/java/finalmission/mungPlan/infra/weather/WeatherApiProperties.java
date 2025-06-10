package finalmission.mungPlan.infra.weather;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Getter
@Validated
@RequiredArgsConstructor
@ConfigurationProperties("weather-api")
public class WeatherApiProperties {

    private final String baseUrl;

    private final String SecretKey;
}
