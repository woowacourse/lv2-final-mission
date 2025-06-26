package finalmission.holiday.business;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class NationalHolidayRestClient {

    public static final String BASE_URL_OF_NATIONAL_HOLIDAY = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService";

    private final RestClient restClient;

    public NationalHolidayRestClient(RestClient.Builder restClientBuilder) {
        URI uri = UriComponentsBuilder.fromUriString(BASE_URL_OF_NATIONAL_HOLIDAY)
                .queryParam("ServiceKey", "daXBZao/MNYLWe/KGylCkymLfoosWXDdrSV9OuHyXWWDrBPgvK25YumL/iggomy88rACi187ZFVSfG7nSbpQlQ==")
                .queryParam("_type", "json")
                .queryParam("numOfRows", 30)
                .build()
                .toUri();
        this.restClient = restClientBuilder
                .baseUrl(uri)
                .build();
    }

    public String getHolidays(int year, int month) {
        return restClient.get()
                .uri(String.format("/getHoliDeInfo?solYear=%d&solMonth=%s", year, String.format("%02d", month)))
                .retrieve()
                .body(String.class);
    }
}
