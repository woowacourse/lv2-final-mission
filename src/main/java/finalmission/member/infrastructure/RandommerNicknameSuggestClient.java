package finalmission.member.infrastructure;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RandommerNicknameSuggestClient implements NicknameSuggestClient {

    private final RestClient restClient;
    private final String secretKey;

    public RandommerNicknameSuggestClient(
            RestClient.Builder restClientBuilder,
            @Value("${nickname-suggestion.randommer.secret-key}") String secretKey,
            @Value("${nickname-suggestion.randommer.base-url}") String baseUrl
    ) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.secretKey = secretKey;
    }

    @Override
    public String getNickname() {
        List response = restClient.get()
                .uri("api/Name?nameType=firstname&quantity=1")
                .header("X-Api-Key", secretKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .body(List.class);
        return response.get(0).toString();
    }

    private void handleError(HttpRequest request, ClientHttpResponse response) {
        throw new RuntimeException("외부 API 통신 에러가 발생했습니다.");
    }
}
