package finalmission.unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import finalmission.infrastructure.randommer.RandommerClient;
import finalmission.service.RandommerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Import(RandommerServiceTest.TestConfig.class)
@RestClientTest(value = RandommerService.class)
public class RandommerServiceTest {

    @Autowired
    private RandommerService randommerService;

    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    void 랜덤_이름_생성() {
        //given
        String apiUrl = "/Name?nameType=firstName&quantity=1";
        String response = "john";
        mockServer
                .expect(requestTo(apiUrl))
                .andRespond(withSuccess(response, MediaType.APPLICATION_JSON));

        //when
        String name = randommerService.generateRandomFirstName();

        //then
        assertThat(name).isEqualTo("john");
    }

    // 테스트 관련 구성 클래스
    @TestConfiguration
    public static class TestConfig {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean
        public RestClient restClient(RestTemplate restTemplate) {
            return RestClient.builder(restTemplate).build();
        }

        @Bean
        public RandommerClient randommerClient(RestClient restClient) {
            return new RandommerClient(restClient);
        }
    }
}
