package finalmission.client;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandommerClient {
    private final RestClient.Builder clientBuilder;
    private final String secretKey;

    public RandommerClient(final RestClient.Builder clientBuilder,
            @Value("${randommer-secret-key}") final String secretKey) {
        this.clientBuilder = clientBuilder;
        this.secretKey = secretKey;
    }

    public String getSingleRandomName() {
        return Objects.requireNonNull(clientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Name")
                        .queryParam("nameType", "fullname")
                        .queryParam("quantity", 1)
                        .build())
                .header("X-Api-Key", secretKey)
                .retrieve()
                .body(String[].class))[0];
    }
}
