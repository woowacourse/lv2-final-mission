package finalmission.external.holiday;

import com.fasterxml.jackson.databind.JsonNode;
import finalmission.external.holiday.dto.SimpleHoliday;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RestDayRestClient {

    private final RestClient restClient;
    private final String baseUrl;
    private final String serviceKey;

    public RestDayRestClient(
            @Value("${api.holiday.base-url}") String baseUrl,
            @Value("${api.holiday.service-key}") String serviceKey
    ) {
        this.baseUrl = baseUrl;
        this.serviceKey = serviceKey;

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public List<SimpleHoliday> getHolidays(int year, int month) {
        String url = String.format(
                "%s?serviceKey=%s&solYear=%d&solMonth=%02d&_type=json",
                baseUrl, serviceKey, year, month
        );

        ResponseEntity<JsonNode> response = restClient.get()
                .uri(url)
                .retrieve()
                .toEntity(JsonNode.class);

        JsonNode items = Objects.requireNonNull(response.getBody())
                .path("response")
                .path("body")
                .path("items")
                .path("item");

        List<SimpleHoliday> holidays = new ArrayList<>();
        if (items.isArray()) {
            for (JsonNode item : items) {
                holidays.add(new SimpleHoliday(
                        item.path("locdate").asText(),
                        item.path("dateName").asText()
                ));
            }
        } else if (items.isObject()) {
            holidays.add(new SimpleHoliday(
                    items.path("locdate").asText(),
                    items.path("dateName").asText()
            ));
        }

        return holidays;
    }
}
