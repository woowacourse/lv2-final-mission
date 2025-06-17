package finalmission.thirdparty;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import finalmission.thirdparty.service.RandomNameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

@RestClientTest(RandomNameService.class)
public class RandomNameServiceTest {

    @Autowired
    private RandomNameService randomNameService;

    @Autowired
    private MockRestServiceServer server;

    @TestConfiguration
    static class TestRandomNameClientConfig {
        @Bean
        public RestClient randomNameClient(RestClient.Builder builder) {
            return builder
                    .baseUrl("https://randommer.io/api/Name")
                    .defaultHeader("X-Api-Key", "test-api-key")
                    .build();
        }
    }

    @Test
    @DisplayName("랜덤 이름을 요청하고 응답을 파싱한다.")
    void getRandomName() {
        // given
        String expectedName = "John Doe";
        String mockJsonArray = "[\"" + expectedName + "\"]";

        server.expect(once(), requestTo("https://randommer.io/api/Name?nameType=fullname&quantity=1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockJsonArray, MediaType.APPLICATION_JSON));

        // when
        String name = randomNameService.getRandomName();

        // then
        assertThat(name).isEqualTo(expectedName);
        server.verify();
    }
}
