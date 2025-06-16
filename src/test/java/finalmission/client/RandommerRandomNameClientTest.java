package finalmission.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.exception.ExternalApiConnectionException;
import finalmission.exception.RandomNameGenerationException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

class RandommerRandomNameClientTest {

    private RandommerRandomNameClient randomNameClient;
    private MockWebServer mockWebServer;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        RestClient restClient = RestClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        randomNameClient = new RandommerRandomNameClient(restClient, "api_key", "generation_url");
    }

    @AfterEach
    void afterEach() throws IOException {
        mockWebServer.shutdown();
    }

    @Nested
    @DisplayName("외부 서버에 랜덤 이름 생성 요청을 보낼 수 있다.")
    public class GenerateRandomNames {

        @DisplayName("정상적으로 외부 서버에 랜덤 이름 생성 요청을 보낼 수 있다.")
        @Test
        void canGenerateRandomNames() {
            // given
            String mockResponseBody = "[\"Pearson\", \"Liliann\"]";
            MockResponse expectedResponse = new MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json")
                    .setBody(mockResponseBody);
            mockWebServer.enqueue(expectedResponse);

            // when
            List<String> names = randomNameClient.generateRandomNames(2);

            // then
            assertThat(names).containsExactlyInAnyOrder("Pearson", "Liliann");
        }

        @DisplayName("연결에 실패할 경우 예외를 발생시킨다.")
        @Test
        void occurExceptionWhenConnectionFail() {
            // given
            RestClient restClient = RestClient.builder()
                    .baseUrl("http://unkown-domain")
                    .build();

            randomNameClient = new RandommerRandomNameClient(
                    restClient, "secret_key", "/api/io");

            // when & then
            assertThatThrownBy(() -> randomNameClient.generateRandomNames(2))
                    .isInstanceOf(ExternalApiConnectionException.class);
        }

        @DisplayName("타임아웃이 발생할 경우 예외를 발생킨다.")
        @Test
        void occurExceptionWhenTimeout() {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(Duration.ofSeconds(1));
            requestFactory.setReadTimeout(Duration.ofSeconds(1));

            RestClient restClient = RestClient.builder()
                    .requestFactory(requestFactory)
                    .baseUrl(mockWebServer.url("/").toString())
                    .build();
            randomNameClient = new RandommerRandomNameClient(restClient, "api_key", "generation_url");

            MockResponse expectedResponse = new MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json")
                    .setHeadersDelay(2, TimeUnit.SECONDS);
            mockWebServer.enqueue(expectedResponse);

            // when & then
            assertThatThrownBy(() -> randomNameClient.generateRandomNames(2))
                    .isInstanceOf(ExternalApiConnectionException.class);
        }

        @DisplayName("4XX 예외응답을 받을 경우 예외를 발생시킨다.")
        @Test
        void occurExceptionWhen4XXResponse() {
            // given
            String responseBody = """
                    {
                        "title": "예외 메세지 입니다."
                    }
                    """;
            MockResponse expectedResponse = new MockResponse()
                    .setResponseCode(404)
                    .addHeader("Content-Type", "application/json")
                    .setBody(responseBody);
            mockWebServer.enqueue(expectedResponse);

            // when & then
            assertThatThrownBy(() -> randomNameClient.generateRandomNames(2))
                    .isInstanceOf(RandomNameGenerationException.class);
        }

        @DisplayName("5XX 예외응답을 받을 경우 예외를 발생시킨다.")
        @Test
        void occurExceptionWhen5XXResponse() {
            // given
            String responseBody = """
                    {
                       "title": "예외 메세지 입니다."
                    }
                    """;
            MockResponse expectedResponse = new MockResponse()
                    .setResponseCode(500)
                    .addHeader("Content-Type", "application/json")
                    .setBody(responseBody);
            mockWebServer.enqueue(expectedResponse);

            // when & then
            assertThatThrownBy(() -> randomNameClient.generateRandomNames(2))
                    .isInstanceOf(RandomNameGenerationException.class)
                    .hasMessage("RamdommerIo 외부 서버 측에서 문제가 발생했습니다.");
        }
    }
}
