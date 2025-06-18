package finalmission.infra.thirdparty;

import finalmission.infra.thirdparty.dto.RestDayRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
        String uri = makeURI(restDayRequest.year(), restDayRequest.month());
        return restTemplate.getForEntity(uri, String.class);
    }

    private String makeURI(int year, int month) {
        return UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("ServiceKey", dataKey)
                .queryParam("solYear", year)
                .queryParam("solMonth", String.format("%02d", month))
                .queryParam("_type", "json")
                .build(false)
                .toUriString();
    }
}

