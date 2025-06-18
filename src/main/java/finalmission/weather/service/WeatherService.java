package finalmission.weather.service;

import finalmission.weather.client.WeatherApiClient;
import finalmission.weather.domain.Weather;
import finalmission.weather.dto.WeatherApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherApiClient weatherApiClient;

    public Weather getSongPaCurrWeather() {
        WeatherApiResponse songPaWeather = weatherApiClient.getSongPaWeather();
        String sky = parseSky(songPaWeather);
        String pop = parsePop(songPaWeather);
        return new Weather(sky, pop);
    }

    private String parseSky(WeatherApiResponse weatherApiResponse) {
        List<WeatherApiResponse.Response.Body.Items.Item> items = weatherApiResponse.getResponse().getBody().getItems().getItem();
        WeatherApiResponse.Response.Body.Items.Item sky = items.stream().filter(item -> item.getCategory().equals("SKY")).findFirst()
                .orElseThrow(() -> new IllegalStateException("카테고리를 찾을 수 없습니다 : SKY"));
        return sky.getFcstValue();
    }

    private String parsePop(WeatherApiResponse weatherApiResponse) {
        List<WeatherApiResponse.Response.Body.Items.Item> items = weatherApiResponse.getResponse().getBody().getItems().getItem();
        WeatherApiResponse.Response.Body.Items.Item pop = items.stream().filter(item -> item.getCategory().equals("POP")).findFirst()
                .orElseThrow(() -> new IllegalStateException("카테고리를 찾을 수 없습니다 : POP"));
        return pop.getFcstValue();
    }
}
