package finalmission.infrastructure;

import java.time.YearMonth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OpenApiClient {

    private final RestClient restClient;
    private final String baseUrl;

    public OpenApiClient(
            RestClient.Builder builder,
            @Value("${openapi.base-url}") String baseUrl,
            @Value("${openapi.secret-key}") String secretKey
    ) {
        this.restClient = builder
                .requestFactory(getSimpleClientHttpRequestFactory())
                .build();
        this.baseUrl = baseUrl + "?serviceKey=" + secretKey;
    }

    private SimpleClientHttpRequestFactory getSimpleClientHttpRequestFactory() {
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return factory;
    }

    public HolidayResponse getHolidays(YearMonth yearMonth) {
        System.out.println(createUrl(yearMonth));
        ResponseEntity<HolidayResponse> responseEntity = restClient.get()
                .uri(createUrl(yearMonth))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(HolidayResponse.class);
        return responseEntity.getBody();
    }

    private String createUrl(YearMonth yearMonth) {
        return baseUrl + String.format("&solYear=%s&solMonth=%02d&_type=json",
                yearMonth.getYear(),
                yearMonth.getMonthValue());
    }


}
