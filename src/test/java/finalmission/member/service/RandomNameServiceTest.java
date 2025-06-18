package finalmission.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class RandomNameServiceTest {

    private final RestClient.Builder testBuilder = RestClient.builder();
    private final MockRestServiceServer server = MockRestServiceServer.bindTo(testBuilder).build();
    @LocalServerPort
    int port;
    @Value("${random-name.secret-key}")
    private String secretKey;

    private RandomNameService randomNameService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        randomNameService = new RandomNameService(secretKey, testBuilder.build());
    }

    @DisplayName("랜덤 이름을 생성할 수 있다.")
    @Test
    void testRandomName() {
        int nameCount = 2;

        server.expect(MockRestRequestMatchers.requestTo(
                        "https://randommer.io/api/Name?nameType=firstname&quantity=" + nameCount))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators
                        .withSuccess("""
                                ["haru", "duru"]
                                """, APPLICATION_JSON));

        String[] randomName = randomNameService.createRandomName(nameCount);

        assertThat(randomName[0]).isEqualTo("haru");
        assertThat(randomName[1]).isEqualTo("duru");
    }
}