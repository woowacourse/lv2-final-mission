package finalmission.payment.service.client;

import finalmission.payment.service.client.dto.KakaoPaymentApproveRequest;
import finalmission.payment.service.client.dto.KakaoPaymentApproveResponse;
import finalmission.payment.service.client.dto.KakaoPaymentCancelRequest;
import finalmission.payment.service.client.dto.KakaoPaymentReadyRequest;
import finalmission.payment.service.client.dto.KakaoPaymentReadyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
public class KakaoPaymentClient {

    @Value("${payment.key}")
    private String SECRET_KEY;

    private final RestClient restClient;

    public KakaoPaymentReadyResponse ready(final KakaoPaymentReadyRequest request) {
        return restClient.post().uri("/ready")
                .header("Authorization", "SECRET_KEY " + SECRET_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(KakaoPaymentReadyResponse.class);
    }

    public KakaoPaymentApproveResponse approve(final KakaoPaymentApproveRequest request) {
        return restClient.post().uri("/approve")
                .header("Authorization", "SECRET_KEY " + SECRET_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(KakaoPaymentApproveResponse.class);
    }

    public void cancel(final KakaoPaymentCancelRequest request) {
        restClient.post().uri("/cancel")
                .header("Authorization", "SECRET_KEY " + SECRET_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(Void.class);
    }
}
