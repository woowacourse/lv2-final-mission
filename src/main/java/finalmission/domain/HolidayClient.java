package finalmission.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.dto.response.HolidayElementResponse;
import finalmission.exception.custom.CannotConnectException;
import finalmission.exception.custom.InvalidDateException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HolidayClient {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final RestClient restClient;
    private final String serviceKey;

    public HolidayClient(final RestClient restClient, @Value("${data-service-key}") final String serviceKey) {
        this.restClient = restClient;
        this.serviceKey = serviceKey;
    }

    public void checkHoliday(LocalDate date) {
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            throw new InvalidDateException("주말에는 예약할 수 없습니다.");
        }

        List<HolidayElementResponse> holidayData = getHolidayData(date);

        if (holidayData.isEmpty()) {
            return;
        }

        for (HolidayElementResponse holidayElementResponse : holidayData) {
            if (holidayElementResponse.isHoliday().equals("Y") && holidayElementResponse.locdate().equals(date)) {
                throw new InvalidDateException("공휴일에는 예약할 수 없습니다.: %s".formatted(holidayElementResponse.dateName()));
            }
        }
    }

    private List<HolidayElementResponse> getHolidayData(final LocalDate date) {

        String body = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/B090041/openapi/service/SpcdeInfoService/getRestDeInfo")
                        .queryParam("solYear", date.getYear())
                        .queryParam("solMonth", String.format("%02d", date.getMonth().getValue()))
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("_type", "json")
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    throw new CannotConnectException(response.getStatusCode().toString());
                })
                .toEntity(String.class)
                .getBody();

        return parseJsonNode(body);
    }

    private List<HolidayElementResponse> parseJsonNode(final String body) {
        try {
            List<HolidayElementResponse> holidayElementResponses = new ArrayList<>();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(body);

            JsonNode responseNode = rootNode.get("response");

            JsonNode bodyNode = responseNode.get("body");
            JsonNode itemsNode = bodyNode.get("items");

            if (!itemsNode.has("item")) {
                return holidayElementResponses;
            }

            JsonNode itemNode = itemsNode.get("item");

            for (int i = 0; i < itemNode.size(); i++) {
                JsonNode element = itemNode.get(i);
                String dateName = element.get("dateName").asText();
                String isHoliday = element.get("isHoliday").asText();
                String locdate = element.get("locdate").asText();
                holidayElementResponses.add(
                        new HolidayElementResponse(dateName, isHoliday, LocalDate.parse(locdate, DATE_FORMATTER)));
            }
            return holidayElementResponses;
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
