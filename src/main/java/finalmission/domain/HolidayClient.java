package finalmission.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.dto.response.HolidayElementResponse;
import finalmission.exception.custom.CannotConnectException;
import finalmission.exception.custom.InvalidDateException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HolidayClient {

    @Value("${data-secret-key}")
    private String SECRET_KEY;

    private final RestClient restClient;

    public HolidayClient(final RestClient restClient) {
        this.restClient = restClient;
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
            if (holidayElementResponse.locdate().equals(date)) {
                throw new InvalidDateException("공휴일에는 예약할 수 없습니다.: %s".formatted(holidayElementResponse.dateName()));
            }
        }
    }

    private List<HolidayElementResponse> getHolidayData(final LocalDate date) {
        try {
            String body = restClient.get()
                    .uri("?serviceKey=%s&solYear=%d&solMonth=%02d&_type=json".formatted(SECRET_KEY, date.getYear(),
                            date.getMonth().getValue()))
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        throw new CannotConnectException(response.getStatusCode().toString());
                    })
                    .toEntity(String.class)
                    .getBody();

            System.out.println(body);

            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> map = objectMapper.readValue(body, new TypeReference<>() {
            });
            System.out.println("========" + map);
            return (ArrayList<HolidayElementResponse>) map.get("item");
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
