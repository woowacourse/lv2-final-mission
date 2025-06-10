package finalmission.util;

import java.time.LocalDate;
import org.springframework.web.client.RestClient;

public class HolidayClient {

    private final RestClient restClient;
    private final String secretKey = "GyilCH8tw6+5ZPnBWto0L6W5f7GA75fG8DsGcB+im28w4PllJF8RYECUnTpXcc0s2gftYiuImoeSuj7h5cvw2w==";

    public HolidayClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getHolidays(LocalDate localDate) {
        int year = localDate.getYear();
        int month = localDate.getDayOfMonth();
        return restClient.get()
                .uri("https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?serviceKey={secretKey}&solYear={year}&solMonth={month}&_type=json",secretKey,year,month)
                .retrieve()
                .body(String.class);
    }
}
