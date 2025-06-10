package finalmission.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RandommerNameGenerator implements NameGenerator {

    private final RestClient restClient;
    private final String apiKey;

    public RandommerNameGenerator(
            @Value("${randommer.uri}") String randommerNameGenerateUri,
            @Value("${randommer.api-key}") String randommerApiKey
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(randommerNameGenerateUri)
                .build();
        this.apiKey = randommerApiKey;
    }

    // TODO : 응답 형식이랑 다를 수 있음
    @Override
    public String generate() {
        return restClient.get()
                .header("X-Api-Key", apiKey)
                .retrieve()
                .body(String.class);
    }
}
