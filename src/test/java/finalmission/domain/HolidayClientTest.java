package finalmission.domain;

import finalmission.dto.response.HolidayElementResponse;
import finalmission.exception.custom.CannotConnectException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class HolidayClientTest {

    static final String BASE_URL = "https://apis.data.go.kr";
    static final String API_URL = BASE_URL
            + "/B090041/openapi/service/SpcdeInfoService/getRestDeInfo?solYear=%d&solMonth=%02d&serviceKey=%s&_type=json";

    final RestClient.Builder testBuilder = RestClient.builder()
            .baseUrl(BASE_URL);

    MockRestServiceServer mockServer = MockRestServiceServer.bindTo(testBuilder).build();

    String serviceKey = "serviceKey";

    HolidayClient holidayClient = new HolidayClient(testBuilder.build(), serviceKey);

    @BeforeEach
    void setUp() {
        mockServer.reset();
    }

    @Test
    @DisplayName("외부 API 통신을 기반으로 공휴일 데이터를 조회한다.")
    void getHolidayData() {
        //given
        String expectedResponse = """
                {
                    "response": {
                        "header": {
                            "resultCode": "00",
                            "resultMsg": "NORMAL SERVICE."
                        },
                        "body": {
                            "items": {
                                "item": [
                                    {
                                        "dateKind": "01",
                                        "dateName": "어린이날",
                                        "isHoliday": "Y",
                                        "locdate": 20250505,
                                        "seq": 1
                                    },
                                    {
                                        "dateKind": "01",
                                        "dateName": "부처님오신날",
                                        "isHoliday": "Y",
                                        "locdate": 20250505,
                                        "seq": 2
                                    },
                                    {
                                        "dateKind": "01",
                                        "dateName": "대체공휴일",
                                        "isHoliday": "Y",
                                        "locdate": 20250506,
                                        "seq": 1
                                    }
                                ]
                            },
                            "numOfRows": 10,
                            "pageNo": 1,
                            "totalCount": 3
                        }
                    }
                }
                """;

        LocalDate date = LocalDate.of(2025, 5, 1);

        mockServer
                .expect(requestTo(API_URL.formatted(date.getYear(), date.getMonth().getValue(), serviceKey)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));

        // when
        List<HolidayElementResponse> actual = holidayClient.getHolidayData(date);

        // then
        assertThat(actual).hasSize(3);
        mockServer.verify();
    }

    @Test
    @DisplayName("외부 API 통신을 실패 시, 예외를 던진다.")
    void throwExceptionWhenCannotConnectHolidayData() {
        //given
        LocalDate date = LocalDate.of(2025, 5, 1);

        mockServer
                .expect(requestTo(API_URL.formatted(date.getYear(), date.getMonth().getValue(), serviceKey)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        // when // then
        assertThatThrownBy(() -> holidayClient.getHolidayData(date))
                .isInstanceOf(CannotConnectException.class)
                .hasMessageContaining(Integer.toString(HttpStatus.BAD_REQUEST.value()));
    }
}
