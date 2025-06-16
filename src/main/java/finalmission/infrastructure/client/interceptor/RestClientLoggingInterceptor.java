package finalmission.infrastructure.client.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

@Slf4j
public class RestClientLoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
        final HttpRequest request,
        final byte[] body,
        final ClientHttpRequestExecution execution
    ) throws IOException {
        logRequest(request, body);
        final ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);

        return response;
    }

    private void logRequest(
        final HttpRequest request,
        final byte[] body
    ) {
        log.info("Request URI: {} Body: {}", request.getURI(), new String(body, StandardCharsets.UTF_8));
    }

    private void logResponse(final ClientHttpResponse response) throws IOException {
        log.info("Response Status: {} Body: {}", response.getStatusCode(),
            new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8));
    }
}
