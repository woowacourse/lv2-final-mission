package finalmission.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PasswordClient {

    private final RestClient restClient;
    private final String secretKey;

    public PasswordClient(
            RestClient.Builder restClientBuilder,
            @Value("${password-recommend.secret-key}") String secretKey
    ) {
        this.restClient = restClientBuilder.build();
        this.secretKey = secretKey;
    }

    public String getRecommendedPassword() {
        String baseUrl = "https://randommer.io/api/Text/Password?length=10&hasDigits=true&hasUppercase=false&hasSpecial=false";
        String body = restClient.get()
                .uri(baseUrl)
                .header("X-Api-Key", secretKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, this::handleError)
                .body(String.class);
        return body.substring(1, body.length() - 1);
    }

    private void handleError(HttpRequest request, ClientHttpResponse response) {
        throw new RuntimeException("외부 API 통신 에러가 발생했습니다.");
    }
}
