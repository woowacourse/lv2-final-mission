package finalmission.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
public class HolidayClient {

//    private final RestClient restClient;
//
//    public HolidayClient(final RestClient restClient) {
//        this.restClient = restClient;
//    }

    private final RestTemplate restTemplate;

    public HolidayClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Object getHoliday() {
        URI requestURI = URI.create("https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?serviceKey=AzBANdz5QM35rSK6564fdA6H0i3pq0eVyrBhvUYTdWSa850frpk1cbFFiDvx7WfsPr8jlVUVM4RzmeSdbsT%2FFQ%3D%3D&solYear=2025&solMonth=05&_type=json");
        String holidayDate = restTemplate.getForObject(requestURI, String.class);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(holidayDate);

        return jsonObject.get("items").getAsString();

//        return restClient.get()
//                .uri("https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?serviceKey=AzBANdz5QM35rSK6564fdA6H0i3pq0eVyrBhvUYTdWSa850frpk1cbFFiDvx7WfsPr8jlVUVM4RzmeSdbsT%2FFQ%3D%3D&solYear=2025&solMonth=05&_type=json")
//                .retrieve()
//                .body(Holidays.class);
    }
}

