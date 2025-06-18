package finalmission.payment.service.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.payment.service.client.dto.KakaoPaymentReadyRequest;
import finalmission.payment.service.client.dto.KakaoPaymentReadyResponse;
import java.io.IOException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RestClientTest
class KakaoPaymentClientTest {

    private MockWebServer mockWebServer;

    private RestClient restClient;

    private KakaoPaymentClient kakaoPaymentClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        restClient = RestClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        kakaoPaymentClient = new KakaoPaymentClient(restClient);

    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void 결제_대기가_성공한다() throws JsonProcessingException {
        // Given
        final KakaoPaymentReadyRequest request = new KakaoPaymentReadyRequest(
                "TC0ONETIME",
                "temp_partner_order_id",
                "temp_partner_user_id",
                "Test Item",
                1,
                1000,
                0,
                "http://localhost:8080/payments/confirm",
                "http://localhost:8080/payments",
                "http://localhost:8080/payments"
        );

        final KakaoPaymentReadyResponse response = new KakaoPaymentReadyResponse(
                "test_tid",
                "https://kakao.com/redirect",
                "https://kakao.com/redirect_mobile",
                "https://kakao.com/redirect_pc",
                "https://kakao.com/redirect_ios",
                "2023-10-01T00:00:00Z"
        );

        final String expectedResponse = objectMapper.writeValueAsString(response);

        mockWebServer.enqueue(new MockResponse()
                .setBody(expectedResponse)
                .addHeader("Content-Type", "application/json"));

        // When
        final KakaoPaymentReadyResponse readyResponse = kakaoPaymentClient.ready(request);

        // Then
        assertThat(readyResponse.next_redirect_pc_url()).isNotNull();
    }

    // TODO: 예외 처리 기능 추가 및 테스트
}
