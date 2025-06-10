package finalmission.infrastructure;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;

@RestClientTest(HolidayClient.class)
@Import(HolidayConfiguration.class)
class HolidayClientTest {

    private static final String SERVICE_KEY = "TIwZTv3h91cLYFYvbl7S47A1DQOaXjeQfvgAahWDXgmDDCp2%2BWeBqL0epS9oHQCy1rqGXeizEIxAtSUz%2BiPnCw%3D%3D";

    @Autowired
    MockRestServiceServer server;
    @Autowired
    HolidayClient holidayClient;

    @DisplayName("공휴일 정보 조회 공공API 요청 성공")
    @Test
    void request() {
        //given
        server.expect(requestTo(String.format("/getRestDeInfo?serviceKey=%s&solYear=%d&solMonth=%d&_type=json", SERVICE_KEY, 2025, 06)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess().contentType(MediaType.APPLICATION_JSON));

        //when
        holidayClient.getHoliday(2025, 06);

        //then
        server.verify();
    }
}
