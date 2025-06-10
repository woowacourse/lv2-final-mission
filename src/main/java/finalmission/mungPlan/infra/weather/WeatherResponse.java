package finalmission.mungPlan.infra.weather;

import java.util.List;

public record WeatherResponse(
        Response response
) {

    public record Response(Body body){

    }

    public record Body(Items items, Integer totalCount) {
    }

    public record Items(List<ForecastItem> item) {
    }

    public record ForecastItem(
            String baseDate,
            String baseTime,
            String category,
            String fcstDate,
            String fcstTime,
            String fcstValue,
            Integer nx,
            Integer ny
    ) {
    }

    public List<ForecastItem> getItems() {
        return response.body().items().item();
    }
}
