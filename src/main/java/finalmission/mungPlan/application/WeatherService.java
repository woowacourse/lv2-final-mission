package finalmission.mungPlan.application;

import finalmission.mungPlan.infra.weather.WeatherClient;
import finalmission.mungPlan.infra.weather.WeatherResponse;
import finalmission.mungPlan.infra.weather.WeatherResponse.ForecastItem;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherClient weatherClient;

    public List<ForecastItem> getAllPopItems() {
        int numOfRows = 50;
        int pageNo = 1;
        int totalCount = -1;
        LocalDate baseDate = LocalDate.now();

        List<ForecastItem> popItems = new ArrayList<>();

        do {
            WeatherResponse response = weatherClient.getWeatherData(baseDate, pageNo, numOfRows);

            if (totalCount == -1) {
                totalCount = response.response().body().totalCount();
            }

            List<ForecastItem> items = response.response().body().items().item();

            popItems.addAll(
                    items.stream()
                            .filter(item -> "POP".equals(item.category()))
                            .toList()
            );

            pageNo++;

        } while ((pageNo - 1) * numOfRows < totalCount);

        return popItems;
    }
}
