package finalmission.email.domain;

import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDomainService {

    private final EmailClient emailClient;

    public void sendEmail(final SendEmailRequest request) {
        emailClient.sendEmail(request);
    }
}
