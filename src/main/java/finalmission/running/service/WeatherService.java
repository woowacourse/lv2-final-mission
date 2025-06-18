package finalmission.running.service;

import finalmission.running.dto.request.ReservationRequest;
import finalmission.running.exception.WeatherException;
import finalmission.running.weather.WeatherApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherApiClient weatherApiClient;

    public void checkRunnableWeather(ReservationRequest request){
        String result = weatherApiClient.checkWeather(request.stn(), request.date(), request.startAt(), request.endTime());

        if (result.contains("비") || result.contains("눈")) {
            throw new WeatherException("눈이나 비가 오는 날에는 러닝 세션을 예약할 수 없습니다.");
        }
    }
}
