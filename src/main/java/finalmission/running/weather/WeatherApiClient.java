package finalmission.running.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class WeatherApiClient {

    private final RestClient restClient;

    @Value("${weather.api.key}")
    private String weatherApiKey;

    // 지역코드 "11B00000"
    public String checkWeather(int stn, LocalDate date, LocalTime startAt, LocalTime endTime) {
        String kstFormat = "yyyy-MM-dd HH:mm:ss";
        LocalDateTime start = date.atTime(startAt);
        LocalDateTime end = date.atTime(endTime);
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(kstFormat);

        String weather = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/api/typ01/url/fct_afs_ws.php")
                .queryParam("reg", "A")
                .queryParam("stn", stn)
                .queryParam("tmfc", 0)
                .queryParam("mode", 1)
                .queryParam("disp", 1)
                .queryParam("authKey", weatherApiKey)
                .build()
            )
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .body(String.class);

            System.out.println(weather);
            return weather;

//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            return objectMapper.readValue(weather, String.class);
//        } catch (JsonProcessingException e) {
//            throw new WeatherException("기상정보가 잘못 전달되었습니다.");
//        }
    }
}
