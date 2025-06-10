package finalmission.ballparkreservation.external;

import finalmission.ballparkreservation.external.dto.HolidayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class HolidayClient {

    private static final String REQUEST_URL = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?serviceKey=%s&solYear=%s&solMonth=%s&_type=%s";

    private static final String KEY = "SOMj9u0mLcWBW4CqEjVKFOmKCj7eMBHoDVRsRlr0P6kpxQslfjcJ%2FSnS0XDeC1%2FHNy%2BVGxOHCxf7FsAy6FjEnw%3D%3D";
    private final RestClient restClient;

    public List<LocalDate> getHolidaysOfYearAndMonth(final LocalDate localDate) {
        int monthValue = localDate.getMonth().getValue();
        String monthVariable = String.valueOf(monthValue);
        if (monthValue < 10) {
            monthVariable = "0" + monthValue;
        }

        String uri = String.format(REQUEST_URL, KEY, localDate.getYear(), monthVariable, "json");
        System.out.println("uri = " + uri);
        HolidayResponse resopnse = restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(HolidayResponse.class)
                .getBody();

        return resopnse.getHolidays();
    }
}
