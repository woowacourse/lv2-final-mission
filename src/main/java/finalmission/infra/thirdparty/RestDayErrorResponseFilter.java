package finalmission.infra.thirdparty;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import lombok.NonNull;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestDayErrorResponseFilter implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public RestDayErrorResponseFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(@NonNull URI url, @NonNull HttpMethod method, ClientHttpResponse response)
            throws IOException {
        String body = new String(response.getBody().readAllBytes());
        JsonNode jsonNode = objectMapper.readTree(body);
        String message = jsonNode.get("message").asText();
        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED || response.getStatusCode().is5xxServerError()) {
            throw new OpenApiException(message);
        }
        throw new OpenApiException(message);
    }
}
