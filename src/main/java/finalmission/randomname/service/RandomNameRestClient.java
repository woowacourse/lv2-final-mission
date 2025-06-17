package finalmission.randomname.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandomNameRestClient {

    private final RestClient restClient;

    public RandomNameRestClient(RestClient randommerRestClient) {
        this.restClient = randommerRestClient;
    }

    public String makeRandomName() {
        return restClient.get()
                .uri("/Name?nameType=firstname&quantity=1")
                .retrieve()
                .onStatus(response -> response.getStatusCode().is4xxClientError())
                .onStatus(response -> response.getStatusCode().is5xxServerError())
                .toEntity(String.class)
                .getBody();
    }

}
