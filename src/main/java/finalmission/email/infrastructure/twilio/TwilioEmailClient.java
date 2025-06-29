package finalmission.email.infrastructure.twilio;

import finalmission.email.domain.EmailClient;
import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
import finalmission.exception.email.EmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.UnknownContentTypeException;

@RequiredArgsConstructor
public class TwilioEmailClient implements EmailClient {

    private static final String SEND_MAIL_PATH = "/v3/mail/send";

    private final String secretKey;
    private final RestClient restClient;

    public void sendEmail(final SendEmailRequest request) {
        final String encodedSecretKey = getSecretKey();

        try {
            restClient.post()
                    .uri(SEND_MAIL_PATH)
                    .header("Authorization", encodedSecretKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .toBodilessEntity();
        } catch (ResourceAccessException e) {
            throw new EmailException(
                    "TWILIO 이메일 전송 API가 응답하지 않습니다."
            );
        } catch (UnknownContentTypeException e) {
            throw new EmailException(
                    "TWILIO 이메일 전송 API의 응답 형식이 올바르지 않습니다."
            );
        }
    }

    private String getSecretKey() {
        return "Bearer " + secretKey;
    }
}
