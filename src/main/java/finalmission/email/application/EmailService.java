package finalmission.email.application;

import finalmission.email.domain.EmailClient;
import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailClient emailClient;

    public void sendEmail(final SendEmailRequest request) {
        emailClient.sendEmail(request);
    }
}
