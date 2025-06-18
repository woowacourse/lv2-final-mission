package finalmission.holiday;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class HolidayClient {

    private final static String baseUrl = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";

    @Value("${api.key.holiday}")
    private String holidaySecretKey;

    private final RestClient client;

    public HolidayClient(@Qualifier("holidayRestClient") RestClient client) {
        this.client = client;
    }

    public KoreaAnniversaries requestKoreaAnniversaries(String year, String month) {
        String uri = createRequestUri(year, month);
        return client.get()
                .uri(uri)
                .retrieve()
                .body(KoreaAnniversaries.class);
    }

    private String createRequestUri(String year, String month) {
        Map<String, String> params = new HashMap<>();
        params.put("serviceKey", holidaySecretKey);
        params.put("solYear", year);
        params.put("solMonth", month);
        params.put("_type", "json");

        StringBuilder uriBuilder = new StringBuilder(baseUrl);
        uriBuilder.append("?");
        params.forEach((key, value) -> uriBuilder.append(key).append("=").append(value).append("&"));
        if (uriBuilder.length() > 0) {
            uriBuilder.deleteCharAt(uriBuilder.length() - 1);
        }
        return uriBuilder.toString();
    }
}
