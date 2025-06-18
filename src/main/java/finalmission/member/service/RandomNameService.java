package finalmission.member.service;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RandomNameService {

    private static final String END_POINT = "https://randommer.io/api/Name?nameType=firstname";
    private final String secretKey;
    private final RestClient restClient;

    public RandomNameService(@Value("${random-name.secret-key}") final String secretKey, RestClient restClient) {
        this.secretKey = secretKey;
        this.restClient = restClient;
    }

    public String[] createRandomName(int number) {
        URI uri = UriComponentsBuilder.fromUriString(END_POINT)
                .queryParam("quantity", number)
                .build()
                .toUri();

        return restClient.get()
                .uri(uri)
                .header("X-Api-Key", secretKey)
                .retrieve()
                .body(String[].class);
    }
}
