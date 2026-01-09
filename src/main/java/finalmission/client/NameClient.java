package finalmission.client;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class NameClient {

    private final RestClient nameRestClient;
    private final String apiKey;

    public NameClient(RestClient nameRestClient, @Value("${name.key}") String apiKey) {
        this.nameRestClient = nameRestClient;
        this.apiKey = apiKey;
    }

    public String getRandomName() {
        return nameRestClient.get()
            .header("X-Api-Key", apiKey)
            .retrieve()
            .body(new ParameterizedTypeReference<List<String>>() {
            })
            .getFirst();
    }
}
