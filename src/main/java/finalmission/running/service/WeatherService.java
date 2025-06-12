package finalmission.running.service;

import finalmission.running.dto.request.ReservationRequest;
import finalmission.running.weather.WeatherApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherApiClient weatherApiClient;

    public boolean checkRunnableWeather(ReservationRequest request){
        String result = weatherApiClient.checkWeather(request.date(), request.startAt(), request.endTime());

        if (result.contains("WB09")
            || result.contains("WB11")
            || result.contains("WB13")
            || result.contains("WB12")) {
            return false;
        }
        return true;
    }
}
