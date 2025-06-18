package finalmission.weather.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.weather.dto.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Component
public class WeatherApiClient {

    private final RestClient weatherClient;
    private final String apiSecretKey;
    private final ObjectMapper objectMapper;

    public WeatherApiClient(RestClient weatherClient, @Value("${weather-api.secret-key}") String apiSecretKey, ObjectMapper objectMapper) {
        this.weatherClient = weatherClient;
        this.apiSecretKey = apiSecretKey;
        this.objectMapper = objectMapper;
    }

    public WeatherApiResponse getSongPaWeather(){

        String localDate = LocalDate.now().toString().replace("-", "");

        String uri = "/getVilageFcst?" +
                "serviceKey=" + apiSecretKey + "&" +
                "pageNo=1&" +
                "numOfRows=10&" +
                "base_date=" + localDate + "&" +
                "base_time=0500&" +
                "dataType=JSON&" +
                "nx=61&" +
                "ny=125";

        System.out.println(uri);

        String response = weatherClient.get()
                .uri(uri)
                .retrieve()
                .body(String.class);

        try {
            if (response.trim().startsWith("<?xml")) {
                XmlMapper xmlMapper = new XmlMapper();
                return xmlMapper.readValue(response, WeatherApiResponse.class);
            } else {
                return objectMapper.readValue(response, WeatherApiResponse.class);
            }
        } catch (Exception e){
            throw new IllegalStateException("기상청 API 응답이 올바르지 않습니다.");
        }
    }
}
