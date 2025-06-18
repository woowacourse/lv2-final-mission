package finalmission.payment.service.client;

import finalmission.payment.service.client.dto.KakaoPaymentApproveRequest;
import finalmission.payment.service.client.dto.KakaoPaymentApproveResponse;
import finalmission.payment.service.client.dto.KakaoPaymentReadyRequest;
import finalmission.payment.service.client.dto.KakaoPaymentReadyResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class KakaoPaymentClientApiTest {

    @Autowired
    private KakaoPaymentClient kakaoPaymentClient;

    @Disabled
    @Test
    void ready_요청이_정상적으로_전송된다() {
        // Given
        KakaoPaymentReadyRequest request = new KakaoPaymentReadyRequest(
                "TC0ONETIME",
                "temp_partner_order_id",
                "temp_partner_user_id",
                "Test Concert - Seat 1A",
                1,
                10000,
                0,
                "http://localhost:8080/payments/confirm",
                "http://localhost:8080/payments",
                "http://localhost:8080/payments"
        );

        // When
        KakaoPaymentReadyResponse response = kakaoPaymentClient.ready(request);

        // Then
        assertThat(response.next_redirect_pc_url()).isNotNull();
    }

    @Disabled
    @Test
    void approve_요청이_정상적으로_전송된다() {
        // Given
        KakaoPaymentApproveRequest request = new KakaoPaymentApproveRequest(
                "TC0ONETIME",
                "temp_tid",
                "temp_partner_order_id",
                "temp_partner_user_id",
                "temp_pg_token"
        );

        // When
        KakaoPaymentApproveResponse response = kakaoPaymentClient.approve(request);

        // Then
        assertThat(response.aid()).isNotNull();
    }
}
