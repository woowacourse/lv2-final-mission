package finalmission.infra.thirdparty;

import finalmission.infra.thirdparty.dto.RestDayRequest;
import java.net.URI;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestDayRestClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String dataKey;

    public RestDayRestClient(
            @Value("${data.base-url}") String baseUrl,
            @Value("${data.api-key}") String dataKey,
            @Qualifier("RestDay") RestTemplate restTemplate,
            RestDayErrorResponseFilter errorHandler
    ) {
        this.baseUrl = baseUrl;
        this.dataKey = dataKey;
        this.restTemplate = restTemplate;
        this.restTemplate.setErrorHandler(errorHandler);
    }

    public ResponseEntity<String> getRestDay(RestDayRequest restDayRequest) {
        URI uri = makeURI(restDayRequest.year(), restDayRequest.month());
        return restTemplate.getForEntity(uri, String.class);
    }

    private URI makeURI(int year, int month) {
        String uriString = String.format("%s?ServiceKey=%s&solYear=%d&solMonth=%02d&_type=json",
                baseUrl, dataKey, year, month);
        return URI.create(uriString);
    }
}

