package finalmission.mungPlan.application;

import static org.junit.jupiter.api.Assertions.*;

import finalmission.mungPlan.IntegrationTest;
import finalmission.mungPlan.infra.weather.WeatherResponse.ForecastItem;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class WeatherServiceTest extends IntegrationTest {

    @Autowired
    WeatherService weatherService;

    @DisplayName("POP 정보만 리스트로 뽑는다.")
    @Test
    void getAllPopDatas() {
        // given

        // when
        List<ForecastItem> allPopItems = weatherService.getAllPopItems();

        // then
        System.out.println("allPopItems = " + allPopItems);
    }

}
