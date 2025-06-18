package finalmission.service;

import finalmission.exception.NotFoundException;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class RiotRestClientErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is2xxSuccessful()) {
            return;
        }

        if (response.getStatusCode().value() == HttpStatus.NOT_FOUND.value()) {
            throw new NotFoundException();
        }

        throw new RuntimeException(response.getBody().toString());
    }
}
