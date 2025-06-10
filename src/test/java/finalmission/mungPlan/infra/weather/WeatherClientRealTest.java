package finalmission.mungPlan.infra.weather;

import static org.assertj.core.api.Assertions.assertThat;

import finalmission.mungPlan.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class WeatherClientRealTest extends IntegrationTest {

    @Autowired
    WeatherClient weatherClient;

    @DisplayName("실제 기상청 단기예보 API 요청")
    @Test
    void realApiTest() {
        WeatherResponse weatherData = weatherClient.getWeatherData(1, 10);
        assertThat(weatherData.getItems()).hasSize(10);
    }

}
