package finalmission.infrastructure.openapi;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

public class HolidayApi {

    private final RestClient restClient;
    private final String requestUrl;

    public HolidayApi(
            @Value("${openapi.public-holidays.request-url}") final String requestUrl,
            final RestClient restClient
    ) {
        this.restClient = restClient;
        this.requestUrl = requestUrl;
    }

    public String requestHoliday() {
        try {
            return restClient
                    .get()
                    .uri(new URI(requestUrl))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(String.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
