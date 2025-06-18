package finalmission.member.infrastructure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandomNameClient {
    private static String NAME_TYPE = "fullname";
    private static Integer QUANTITY = 1;
    private final RestClient restClient;

    public RandomNameClient(@Qualifier("nameApiRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public String getRandomName() {
        return restClient.get()
                .uri(uriBuilder ->
                        uriBuilder.queryParam("nameType", NAME_TYPE)
                                .queryParam("quantity", QUANTITY)
                                .build())
                .retrieve()
                .body(String.class);
    }
}
