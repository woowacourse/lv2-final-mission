package finalmission.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.exception.PaymentApproveException;
import finalmission.presentation.dto.PaymentApproveResponseDto;
import finalmission.presentation.dto.PaymentInfo;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.http.HttpStatusCode;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;

public class TossPaymentClient {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final String secretKey;

    public TossPaymentClient(RestClient.Builder restClientBuilder, ObjectMapper objectMapper, String secretKey) {
        this.restClient = restClientBuilder.build();
        this.objectMapper = objectMapper;
        this.secretKey = secretKey;
    }

    public PaymentApproveResponseDto approvePayment(PaymentInfo paymentInfo) {
        String encodedSecretKey = Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
        return restClient.post()
                .uri("/v1/payments/confirm")
                .body(paymentInfo)
                .header("Authorization", "Basic " + encodedSecretKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    InputStream responseBodyStream = response.getBody();
                    String rawResponseBody = StreamUtils.copyToString(responseBodyStream, StandardCharsets.UTF_8);
                    throw objectMapper.readValue(rawResponseBody, PaymentApproveException.class);
                })
                .body(PaymentApproveResponseDto.class);
    }

}
