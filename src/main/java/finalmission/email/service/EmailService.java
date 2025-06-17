package finalmission.email.service;

import finalmission.email.dto.Content;
import finalmission.email.dto.Email;
import finalmission.email.dto.Personalization;
import finalmission.email.dto.SendGridRequest;
import finalmission.reservation.dto.ReservationResponse;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final RestClient emailClient;
    private final String fromEmail;
    private final String key;

    public EmailService(RestClient emailClient, @Value("${spring.sendgrid.from}") String fromEmail, @Value("${spring.sendgrid.api-key}") String key) {
        this.emailClient = emailClient;
        this.fromEmail = fromEmail;
        this.key = key;
    }

    public void sendEmail(String memberEmail, ReservationResponse reservationResponse) {
        SendGridRequest request = getSendGridRequest(memberEmail, reservationResponse);

        LOGGER.info("SENDGRID 메일 발송 요청: {}", request);

        emailClient.post()
                .header("Authorization", "Bearer " + key)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(
                        status -> status.value() != 202,
                        (req, res) -> LOGGER.info("SENDGRID 메일 발송 실패: 상태코드 {} | 응답 {} | 헤더 {}",
                                res.getStatusCode(),
                                res.getBody(),
                                res.getHeaders())
                );
    }

    private SendGridRequest getSendGridRequest(String memberEmail, ReservationResponse response) {
        Email from = new Email(fromEmail);
        Email to = new Email(memberEmail);
        String subject = "회의실 예약 완료";
        List<Personalization> personalizations = List.of(new Personalization(List.of(to), subject));
        List<Content> content = List.of(new Content("text/plain", response.room().name() + " 회의실 예약이 완료되었습니다."));
        return new SendGridRequest(personalizations, from, content);
    }
}
