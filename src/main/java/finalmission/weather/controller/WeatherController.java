package finalmission.weather.controller;

import finalmission.weather.domain.Weather;
import finalmission.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weathers")
    public ResponseEntity<Weather> currentSongPaWeather() {
        Weather songPaCurrWeather = weatherService.getSongPaCurrWeather();
        return ResponseEntity.ok(songPaCurrWeather);
    }
}
