package finalmission.common.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.util.DateFormatUtil;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class PublicHolidayClient {

    @Value("${holiday.secret.key}")
    private String holidayApiKey;

    private final RestClient restClient;

    public boolean checkPublicHoliday(LocalDate localDate) {
        String basePath = "/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";
        String encodedKey = URLEncoder.encode(holidayApiKey, StandardCharsets.UTF_8);

        String queryParams = "serviceKey=" + encodedKey
                + "&solYear=" + localDate.getYear()
                + "&solMonth=" + String.format("%02d", localDate.getMonthValue())
                + "&_type=json";

        String finalPath = basePath + "?" + queryParams;

        String response = restClient.get()
                .uri(finalPath)
                .retrieve()
                .body(String.class);

        JsonNode node;
        try {
            node = new ObjectMapper().readTree(response);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("json parsing error");
        }

        JsonNode item = node.path("response").path("body").path("items").path("item");
        for (int i = 0; i < item.size(); i++) {
            String candidate = item.get(i).path("locdate").asText();
            if (localDate.isEqual(DateFormatUtil.toLocalDate(candidate))) {
                return true;
            }
        }
        return false;
    }
}
