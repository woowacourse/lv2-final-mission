package finalmission.infrastructure;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
                .baseUrl(baseUrl)
                .build();
    }

    public List<String> getRandomNames(String nameType, int quantity) {
        String url = String.format("?nameType=%s&quantity=%d", nameType, quantity);

        ResponseEntity<List> response = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(List.class);

        return response.getBody();
    }
}
