package finalmission.mungPlan.infra.weather;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public class WeatherClient {

    private final RestClient restClient;
    private final String serviceKey;

    public WeatherClient(final RestClient restClient, final String serviceKey) {
        this.restClient = restClient;
        this.serviceKey = serviceKey;
    }

    public WeatherResponse getWeatherData(LocalDate baseDate, int pageNo, int numOfRows) {
        try {
            UriComponents uriComponents = UriComponentsBuilder.fromUriString("/getVilageFcst")
                    .queryParam("serviceKey", serviceKey)
                    .queryParam("dataType", "JSON")
                    .queryParam("pageNo", pageNo)
                    .queryParam("numOfRows", numOfRows)
                    .queryParam("base_date", baseDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                    .queryParam("base_time", "0500")
                    .queryParam("nx", 55)
                    .queryParam("ny", 127)
                    .build();

            return restClient.get()
                    .uri(uriComponents.toUriString())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(WeatherResponse.class);
        } catch (ResourceAccessException ex) {
            log.error("Resource Access Exception:", ex);
            if (ex.getCause() instanceof SocketTimeoutException) {
                log.warn("토스 API 요청 타임아웃");
                throw new IllegalStateException("결제 시스템이 응답하지 않아 시간이 초과되었습니다.");
            }
            throw ex;
        }
    }
}
