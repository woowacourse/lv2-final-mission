package finalmission.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.config.ClientConfiguration;
import finalmission.dto.HolidayApiResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class HolidayService {

    private static final Logger logger = LoggerFactory.getLogger(HolidayService.class);
    private final RestClient restClient;
    private final ClientConfiguration clientConfiguration;

    public HolidayService(
            @Qualifier("holidayClient") RestClient restClient,
            ObjectMapper objectMapper,
            ClientConfiguration clientConfiguration) {
        this.restClient = restClient;
        this.clientConfiguration = clientConfiguration;
    }

    public boolean isHoliday(LocalDate date) {
        try {
            String year = String.valueOf(date.getYear());
            String month = String.format("%02d", date.getMonthValue());
            String serviceKey = clientConfiguration.getServiceKey();

            String fullUrl = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?serviceKey="
                    + serviceKey + "&solYear=" + year + "&solMonth=" + month + "&_type=json";

            HolidayApiResponse response = restClient.get()
                    .uri(fullUrl)
                    .retrieve()
                    .body(HolidayApiResponse.class);

            if (response != null && response.getResponse() != null &&
                    response.getResponse().getBody() != null &&
                    response.getResponse().getBody().getItems() != null &&
                    response.getResponse().getBody().getItems().getItem() != null) {
                int targetDate = Integer.parseInt(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));

                for (HolidayApiResponse.HolidayItem item : response.getResponse().getBody().getItems().getItem()) {
                    if (targetDate == item.getLocdate() && "Y".equals(item.getIsHoliday())) {
                        return true;
                    }
                }
            }

            int dayOfWeek = date.getDayOfWeek().getValue();
            return dayOfWeek == 6 || dayOfWeek == 7;

        } catch (Exception e) {
            int dayOfWeek = date.getDayOfWeek().getValue();
            return dayOfWeek == 6 || dayOfWeek == 7;
        }
    }
}
