package finalmission.infrastructure;

import org.springframework.web.client.RestClient;

public class RandomNameClient {

    private static final String API_KEY = "1f1221731805408a8d552752127b95af";

    private final RestClient restClient;

    public RandomNameClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getRandomName(){
        return restClient.get()
                .header("x-api-key", API_KEY)
                .retrieve()
                .body(String.class);
    }
}
