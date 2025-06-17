package finalmission.running.service;

import finalmission.member.dto.response.LoginInfo;
import finalmission.running.dto.request.ReservationRequest;
import finalmission.running.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunningService {

    private final RunningReservationService runningReservationService;
    private final WeatherService weatherService;

    public ReservationResponse createRunningReservation(ReservationRequest request, LoginInfo loginInfo) {
        weatherService.checkRunnableWeather(request);
        return runningReservationService.createRunningReservation(request, loginInfo);
    }
}
