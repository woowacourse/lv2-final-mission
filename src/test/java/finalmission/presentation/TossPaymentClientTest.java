package finalmission.presentation;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import finalmission.business.service.TossPaymentClientConfig;
import finalmission.presentation.dto.PaymentInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;

@RestClientTest(value = {TossPaymentClientConfig.class, TossPaymentClient.class})
class TossPaymentClientTest {
    @Autowired
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private TossPaymentClient tossPaymentClient;
    @Value("${payment.secretKey}")
    private String secretKey;

    @Test
    @DisplayName("결제 승인 요청")
    void test1() {
        // given
        String url = "https://api.tosspayments.com/v1/payments/confirm";
        // when
        mockRestServiceServer.expect(requestTo(url)).andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());
        // then
        Assertions.assertThatCode(
                () -> tossPaymentClient.approvePayment(PaymentInfo.of("PaymentKey", "orderId", 20_000L)));
    }

}
