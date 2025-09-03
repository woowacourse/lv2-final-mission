package finalmission.running.weather;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class WeatherApiIntegrationTest {

    @Autowired
    private WeatherApiClient weatherApiClient;

    @Test
    void testRealWeatherApiCall() {
        // 실제 동작 테스트를 위해 유효한 지역 코드와 시간 입력
        int stn = 108;// 수도권 예시
        LocalDate date = LocalDate.now();
        LocalTime startAt = LocalTime.of(9, 0);
        LocalTime endAt = LocalTime.of(18, 0);

        String response = weatherApiClient.checkWeather(stn, date, startAt, endAt);
    }
}
