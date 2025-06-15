package finalmission.member.infrastructure;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;

@Component
public class RandomNameClient {

    private final RestClient restClient;

    public RandomNameClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getRandomName() {
        return Arrays.stream(restClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/Name");
                    uriBuilder.queryParam("nameType", "firstname");
                    uriBuilder.queryParam("quantity", 1);
                    return uriBuilder.build();
                })
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new RuntimeException("외부 API 처리 과정에서 오류가 발생했습니다.");
                })
                .body(String[].class))
                .findFirst()
                .get();
    }
}
