package finalmission.infrastructure;

import finalmission.application.support.exception.ExternalApiException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class RandomNameClient {

    private final RestClient restClient;

    public RandomNameClient(
            RestClient.Builder builder,
            @Value("${randommer.api-key}") String apiKey,
            @Value("${randommer.base-url}") String baseUrl) {
        this.restClient = builder
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Api-Key", apiKey)
                .requestFactory(getRequestFactory())
                .baseUrl(baseUrl)
                .build();
    }

    private ClientHttpRequestFactory getRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return factory;
    }

    public List<String> getRandomNames(String nameType, int quantity) {
        String url = String.format("?nameType=%s&quantity=%d", nameType, quantity);

        try {
            ResponseEntity<List> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .toEntity(List.class);
            return response.getBody();
        } catch (ResourceAccessException e) {
            throw new ExternalApiException("리소스 접근 에러", e);
        } catch (RuntimeException e) {
            throw new ExternalApiException("예상치 못한 예외 발생", e);
        }
    }
}
