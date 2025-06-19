package finalmission.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

@RestClientTest(RandomNameClient.class)
@Import(RandomNameConfiguration.class)
class RandomNameClientTest {

    @Autowired
    private RestClient.Builder restClientBuilder;
    private MockRestServiceServer server;
    private RandomNameClient randomNameClient;

    @BeforeEach
    void setUp() {
        server = MockRestServiceServer.bindTo(restClientBuilder).build();
        randomNameClient = new RandomNameClient(restClientBuilder);
    }

    @DisplayName("랜덤 이름 생성 요청 성공")
    @Test
    void request() {
        //given
        String expectedBody = """
                [
                  "Aina"
                ]
                """;

        server.expect(requestTo("https://randommer.io/api/Name?nameType=firstname&quantity=1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(MockRestResponseCreators.withSuccess()
                        .body(expectedBody)
                        .contentType(MediaType.APPLICATION_JSON)
                );

        //when
        String actualName = randomNameClient.getFirstName();

        //then
        server.verify();
        assertThat(actualName).isEqualTo("Aina");
    }
}
