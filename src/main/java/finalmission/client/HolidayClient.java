package finalmission.client;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;
import org.springframework.web.util.UriBuilderFactory;

@Component
public final class HolidayClient {

    private final RestClient restClient;
    private final HolidayProperties holidayProperties;

    public HolidayClient(HolidayProperties holidayProperties) {
        this.restClient = RestClient.builder()
                .baseUrl(holidayProperties.baseUrl())
//                .uriBuilderFactory(createUriBuilderFactory())
                .requestFactory(createRequestFactory())
                .build();
        this.holidayProperties = holidayProperties;
    }

    private UriBuilderFactory createUriBuilderFactory() {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(EncodingMode.NONE);
        return uriBuilderFactory;
    }

    private SimpleClientHttpRequestFactory createRequestFactory() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(Duration.ofSeconds(5));
        requestFactory.setReadTimeout(Duration.ofSeconds(35));
        return requestFactory;
    }

    public Map<LocalDate, List<HolidayItem>> getHolidays(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonth().getValue();
        ResponseEntity<HolidayResponse> responseEntity = restClient.get()
                .uri("?secretKey=" + holidayProperties.secretKey()
                        + "&solYear=" + year
                        + "&solMonth=" + month
                        + "&_type=json"
                )
                .retrieve()
                .toEntity(HolidayResponse.class);
        HolidayItem[] item = responseEntity.getBody().response().body().items().item();
        Map<LocalDate, List<HolidayItem>> holidays = new HashMap<>();
        for (HolidayItem holidayItem : item) {
            LocalDate localDate = LocalDate.parse(holidayItem.locdate());
            holidays.computeIfAbsent(localDate, k -> new ArrayList<>()).add(holidayItem);
        }
        return holidays;
    }
}
