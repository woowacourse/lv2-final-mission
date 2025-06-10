package finalmission.mungPlan.infra.weather;

import java.util.List;

public record WeatherResponse(
        List<WeatherResponseUnit> item
){
    public record WeatherResponseUnit(
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
}
