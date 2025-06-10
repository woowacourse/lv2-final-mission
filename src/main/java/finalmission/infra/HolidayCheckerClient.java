package finalmission.infra;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import finalmission.domain.HolidayChecker;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class HolidayCheckerClient implements HolidayChecker {
    private static final String SERVICE_KEY = "xSrbbK33xy8wA8Tcy7oIKWhgyc6qIDBu/oaiZaYZoU6sK9Oe2O4ZV3zZrY7bKklHMoJtqrhYLephXjFG1yhP0A==";
    private final RestTemplate restTemplate;
    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public boolean isHoliday(final LocalDate date) {
        var solYear = date.getYear();
        var solMonth = date.getMonthValue();
        var uri = String.format("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?solYear=%d&solMonth=%02d&ServiceKey=%s", solYear, solMonth, SERVICE_KEY);
        System.out.println("uri = " + uri);
        var apiResponse = restTemplate.postForEntity(URI.create(uri), null, String.class);
        System.out.println("apiResponse.getBody() = " + apiResponse.getBody());
        return true;
//        System.out.println("apiResponse.getBody() = " + apiResponse.getBody().items);
//        return apiResponse.getBody().items.holidays().stream().anyMatch(holiday -> holiday.locdate().equals(date));
    }

    @JacksonXmlRootElement(localName = "response")
    public static class HolidaysResponse {

        @JacksonXmlProperty(localName = "items")
        private HolidaysResponseItems items;
    }

    public static class HolidaysResponseItems {

        private List<Holiday> items;

        @JacksonXmlProperty(localName = "item")
        @JacksonXmlElementWrapper(useWrapping = false)
        public List<Holiday> holidays() {
            return items;
        }

        public void setItems(List<Holiday> items) {
            this.items = items;
        }
    }

    private record Holiday(
        @JacksonXmlProperty(localName = "dateKind")
        int dateKind,
        @JacksonXmlProperty(localName = "dateName")
        String dateName,
        @JacksonXmlProperty(localName = "isHoliday")
        boolean isHoliday,
        @JacksonXmlProperty(localName = "locdate")
        LocalDate locdate,
        @JacksonXmlProperty(localName = "seq")
        int seq
    ) {

    }
}
