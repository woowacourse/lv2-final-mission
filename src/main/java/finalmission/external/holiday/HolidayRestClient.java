package finalmission.external.holiday;

import finalmission.external.holiday.dto.HolidayResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class HolidayRestClient {

    private final String SECRET_KEY = "GsCSc1zx%2F2S9NbG7pehp32d4ftyFpX4mMd%2FjFSFZYx6ujG0ruVNWFBuHUwLB7NH1izSsUG%2FG7LQHpmJMDvxaYQ%3D%3D";

    private final RestClient restClient;

    public HolidayRestClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService")
                .defaultHeader(HttpHeaders.AUTHORIZATION, SECRET_KEY)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .build();
    }

    public boolean checkHoliday(LocalDate date) {
        List<HolidayResponse> holidayInformations = getHolidayInformations(String.valueOf(date.getYear()));
        return holidayInformations.stream()
                .anyMatch(holiday -> holiday.date() == date);
    }

    public List<HolidayResponse> getHolidayInformations(String year) {
        HolidayResponse[] responses = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/getRestDeInfo")
                        .queryParam("solYear", year)
                        .build()
                )
                .retrieve()
                .body(HolidayResponse[].class);
        return Arrays.asList(Objects.requireNonNull(responses));
    }
}
