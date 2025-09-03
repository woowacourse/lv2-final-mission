package finalmission.common.client;

import finalmission.dto.EmailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailClient {

    private final RestClient restClient;

    public void sendSuccessEmail(EmailRequest request) {
        // TODO : 에러처리
        restClient.post()
                .uri("/v3/mail/send")
                .body(request)
                .retrieve()
                .toBodilessEntity();
        log.info("Email sent successfully");
    }
}
