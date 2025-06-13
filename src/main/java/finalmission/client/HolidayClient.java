package finalmission.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class HolidayClient {

    private final static String SERVICE_KEY = "AzBANdz5QM35rSK6564fdA6H0i3pq0eVyrBhvUYTdWSa850frpk1cbFFiDvx7WfsPr8jlVUVM4RzmeSdbsT%2FFQ%3D%3D";

    private final ObjectMapper objectMapper;
    private final RestClient restClient;


    public HolidayClient(final ObjectMapper objectMapper, final RestClient restClient) {
        this.objectMapper = objectMapper;
        this.restClient = restClient;
    }

    public List<LocalDate> getHoliday(int year, String month) {
        try {
            URI requestUri = buildURI(year, month);

            String jsonBody = restClient.get()
                    .uri(requestUri)
                    .retrieve()
                    .body(String.class);

            return parseHolidays(jsonBody);
        } catch (JsonProcessingException | URISyntaxException ex) {
            throw new IllegalStateException();
        }
    }

    private URI buildURI(final int year, final String month) throws URISyntaxException {
        StringBuilder uri = new StringBuilder("https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?")
                .append("serviceKey=" + SERVICE_KEY)
                .append("&solYear=" + year)
                .append("&solMonth=" + month)
                .append("&_type=json");
        return new URI(uri.toString());
    }

    private List<LocalDate> parseHolidays(final String jsonBody) throws JsonProcessingException {
        JsonNode body = objectMapper.readTree(jsonBody)
                                    .path("response")
                                    .path("body");
        int totalCount = body.get("totalCount").asInt();
        if (totalCount == 0) {
            return Collections.emptyList();
        }

        List<LocalDate> holidays = new ArrayList<>();
        for (JsonNode item : body.path("items").path("item")) {
            holidays.add(parseLocalDate(item));
        }

        return holidays;
    }

    private static LocalDate parseLocalDate(final JsonNode item) {
        String locdate = item.path("locdate").asText();
        return LocalDate.of(
                Integer.parseInt(locdate.substring(0, 4)),
                Integer.parseInt(locdate.substring(4, 6)),
                Integer.parseInt(locdate.substring(6, 8))
        );
    }
}

