package finalmission.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.exception.ExternalApiConnectionException;
import finalmission.exception.RandomNameGenerationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

public class RandommerRandomNameClient implements RandomNameClient {

    private final ObjectMapper objectMapper;
    private final RestClient restClient;
    private final String apiKey;
    private final String generationUrl;

    public RandommerRandomNameClient(RestClient restClient, String apiKey,
            String generationUrl) {
        this.objectMapper = new ObjectMapper();
        this.restClient = restClient;
        this.apiKey = apiKey;
        this.generationUrl = generationUrl;
    }

    @Override
    public List<String> generateRandomNames(int quantity) {
        try {
            return requestGenerateRandomName(quantity);
        } catch (RestClientException restClientException) {
            throw new ExternalApiConnectionException("외부 서버와 연결이 불안정합니다.");
        }
    }

    private List<String> requestGenerateRandomName(int quantity) {
        JsonNode root = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(generationUrl)
                        .queryParam("nameType", "firstname")
                        .queryParam("quantity", quantity)
                        .build())
                .header("X-Api-Key", apiKey)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    String errorMessage = make4xxExceptionMessage(response);
                    throw new RandomNameGenerationException("RamdommerIo 외부 서버 : " + errorMessage);
                }))
                .onStatus(HttpStatusCode::is5xxServerError, ((request, response) -> {
                    throw new RandomNameGenerationException("RamdommerIo 외부 서버 측에서 문제가 발생했습니다.");
                }))
                .body(JsonNode.class);
        return parsingResponse(root);
    }

    private String make4xxExceptionMessage(ClientHttpResponse response) {
        try {
            JsonNode errorRoot = objectMapper.readTree(response.getBody());
            String title = errorRoot.get("title").asText();
            return String.format("RamdommerIo 외부 서버 예외 : %s", title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> parsingResponse(JsonNode root) {
        List<String> names = new ArrayList<>();
        if (root.isArray()) {
            for (JsonNode nameNode : root) {
                names.add(nameNode.asText());
            }
        }
        return names;
    }
}
