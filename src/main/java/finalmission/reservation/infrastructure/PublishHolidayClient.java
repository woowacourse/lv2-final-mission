package finalmission.reservation.infrastructure;

import finalmission.reservation.infrastructure.dto.response.ConfirmHolidayResponse;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PublishHolidayClient {

    private final RestClient restClient;
    private final String secretKey;

    public PublishHolidayClient(final RestClient restClient, final String secretKey) {
        this.restClient = restClient;
        this.secretKey = secretKey;
    }

    public void isHoliday(LocalDate date) {
        String uri = UriComponentsBuilder
                .fromUriString("")
                .queryParam("ServiceKey", secretKey)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 31)
                .queryParam("solYear", date.getYear())
                .queryParam("solMonth", String.format("%02d", date.getMonthValue()))
                .queryParam("_type", "json")
                .build()
                .toUriString();

        ConfirmHolidayResponse response = restClient.get()
                .uri(uri)
                .retrieve()
                .body(ConfirmHolidayResponse.class);

        int targetDate = Integer.parseInt(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

        if (validateContainHoliday(targetDate, response)) {
            throw new IllegalArgumentException("공휴일은 예약할 수 없습니다.");
        }
    }

    private boolean validateContainHoliday(final int targetDate, final ConfirmHolidayResponse response) {
        return response.response().body().items().itemList().stream()
                .anyMatch(item -> item.locdate() == targetDate);
    }
}
