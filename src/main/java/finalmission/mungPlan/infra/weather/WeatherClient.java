package finalmission.mungPlan.infra.weather;

import java.net.SocketTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Slf4j
public class WeatherClient {

    private final RestClient restClient;
    private final String secretKey;

    public WeatherClient(final RestClient restClient, final WeatherApiProperties weatherApiProperties) {
        this.restClient = restClient;
        this.secretKey = weatherApiProperties.getSecretKey();
    }

    public WeatherResponse getWeatherInfos() {
        try {
            return restClient.post()
                    .uri("/getVilageFcst/{dataType}{pageNo}{base_date}{time}{nx}{ny}",
                            "JSON", 1, "20250610", "0500", "55", "127")
                    .header("Authorization", secretKey)
                    .contentType(MediaType.APPLICATION_JSON)
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
