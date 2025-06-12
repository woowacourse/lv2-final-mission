package finalmission.running.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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

        if (result.equals("WB09") || result.equals("WB11") || result.equals("WB13") || result.equals("WB12")) {
            return false;
        }
        return true;
    }
}
