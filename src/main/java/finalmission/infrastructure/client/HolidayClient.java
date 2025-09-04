package finalmission.infrastructure.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.domain.Holiday;
import finalmission.domain.HolidayExtractor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class HolidayClient implements HolidayExtractor {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String holidaySecretKey;

    @Override
    public List<Holiday> extract(int year, int month) {
        ResponseEntity<String> result = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/getRestDeInfo")
                        .queryParam("serviceKey", holidaySecretKey)
                        .queryParam("solYear", year)
                        .queryParam("solMonth", String.format("%02d", month))
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new IllegalArgumentException(response.getStatusText());
                })
                .toEntity(String.class);

        List<HolidayInfo> holidayInfos = mapToHolidayInfos(result.getBody());

        return holidayInfos.stream()
                .map(holidayInfo -> new Holiday(holidayInfo.dateName(), LocalDate.parse(holidayInfo.locdate(), DATE_FORMATTER)))
                .toList();
    }

    private List<HolidayInfo> mapToHolidayInfos(String jsonBody) {
        try {
            JsonNode root = objectMapper.readTree(jsonBody);
            JsonNode items = root.path("response")
                    .path("body")
                    .path("items")
                    .path("item");
            if (!items.isArray()) {
                return Collections.emptyList();
            }

            return StreamSupport.stream(items.spliterator(), false)
                    .map(item -> new HolidayInfo(
                            item.path("locdate").asText(),
                            item.path("dateName").asText()
                    ))
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("json 파싱에 실패하였습니다", e);
        }
    }
}
