package finalmission.holiday.business;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NationalHolidayRestClientTest {

    @Autowired
    private NationalHolidayRestClient nationalHolidayRestClient;

    @Test
    void 해당_년월에_해당하는_공휴일들을_가져올_수_있다(){
        // Given
        int year = 2025;
        int month = 5;

        // When
        String response  = nationalHolidayRestClient.getHolidays(year, month);
        System.out.println(response);

        // Then
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode holidays = root.path("response").path("body").path("items").path("item");
            assertThat(holidays.toString()).containsAnyOf("어린이날", "부처님오신날", "대체공휴일");
        } catch (JsonProcessingException exception) {
            Assertions.fail("외부 API 요청에 실패하였습니다.");
        }
    }
}