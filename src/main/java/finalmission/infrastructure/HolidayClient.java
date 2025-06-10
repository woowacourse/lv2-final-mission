package finalmission.infrastructure;

import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersSpec.ConvertibleClientHttpResponse;

public class HolidayClient {

    private static final String BASE_URL = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService";
    private static final String SERVICE_KEY = "TIwZTv3h91cLYFYvbl7S47A1DQOaXjeQfvgAahWDXgmDDCp2%2BWeBqL0epS9oHQCy1rqGXeizEIxAtSUz%2BiPnCw%3D%3D";

    private final RestClient restClient;

    public HolidayClient(RestClient.Builder builder) {
        this.restClient = builder.baseUrl(BASE_URL).build();
    }

    public List<Holiday> getHoliday(int year, int month) {
        var uri = String.format("/getRestDeInfo?serviceKey=%s&solYear=%d&solMonth=%d&_type=json", SERVICE_KEY, year,
                month);

        HolidayResponse holidayResponse = restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .exchange((request, response) -> convert(response));

        return holidayResponse.getHolidays();
    }

    private HolidayResponse convert(ConvertibleClientHttpResponse response) {
        return response.bodyTo(HolidayResponse.class);
    }
}
