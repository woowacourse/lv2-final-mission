package finalmission.email.domain;

import finalmission.email.infrastructure.twilio.dto.SendEmailRequest;

public interface EmailClient {

    void sendEmail(SendEmailRequest request);
}
